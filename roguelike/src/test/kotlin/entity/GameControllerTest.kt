package entity

import engine.*
import entity.models.Monster
import entity.models.User
import entity.models.Wall
import entity.models.interaction.BashInteractionDecorator
import entity.models.interaction.InteractionStrategy
import entity.models.interaction.UserInteraction
import entity.models.monsters.MonsterStrategyFactory
import generator.*
import generator.generators.base.IdGenerator
import generator.info.MonsterInfo
import inventory.containers.DefaultContainer
import inventory.items.ItemWithAmount
import inventory.items.equipments.body.Armor
import org.junit.jupiter.api.Test
import kotlin.test.*

class GameControllerTest {

    private val userHealth = 100
    private val userAttack = 100

    private fun withDefault(useBash: Boolean = false, block: (GameController, IdGenerator) -> Unit) {
        val engine = GameEngine()

        val scene = GameScene()
        val idGenerator = IdGenerator()

        val gameController = GameController(engine) {
            val user = User(
                GameObject(idGenerator.generate(), Position(0, 0), RectShape()),
                Characteristics(userHealth, userAttack, 0),
                engine,
                this
            ) {
                var interaction: InteractionStrategy = UserInteraction(it)
                if (useBash) {
                    interaction = BashInteractionDecorator(interaction, 1.0)
                }
                interaction
            }

            scene.registerObject(user.gameObject)

            GameMap(
                listOf(
                    Level("", "", scene, listOf(user))
                )
            )
        }

        block(gameController, idGenerator)
    }

    private fun createMonster(
        gameController: GameController,
        monsterInfo: MonsterInfo
    ): Monster {
        val strategyFactory = MonsterStrategyFactory()

        val monster = Monster(
            monsterInfo.gameObject,
            monsterInfo.characteristics,
            monsterInfo.name,
            DefaultContainer(monsterInfo.items),
            monsterInfo.type,
            monsterInfo.style,
            strategyFactory.getStrategy(monsterInfo.type, gameController),
            gameController.engine,
            gameController
        )

        gameController.currentLevel.addEntity(monster)

        return monster
    }

    private fun createMonsterInfo(
        idGenerator: IdGenerator,
        position: Position = Position(2, 2),
        type: MonsterType = PassiveMonsterType,
        characteristics: Characteristics = Characteristics(200, 50, 0),
        items: List<ItemWithAmount> = emptyList()
    ) = MonsterInfo(
        "",
        ScifiStyle,
        type,
        GameObject(idGenerator.generate(), position, RectShape()),
        characteristics,
        items
    )

    @Test
    fun testDeathState() = withDefault { gameController, _ ->
        assertEquals(GameState.Default, gameController.state)
        gameController.userDeath()
        assertEquals(GameState.Death, gameController.state)
    }

    @Test
    fun testWinState() = withDefault { gameController, idGenerator ->
        val monsterInfo = createMonsterInfo(idGenerator)
        val monster = createMonster(gameController, monsterInfo)
        assertEquals(GameState.Default, gameController.state)
        gameController.tick()
        assertEquals(GameState.Default, gameController.state)
        gameController.currentLevel.removeEntity(monster.id)
        gameController.tick()
        assertEquals(GameState.Win, gameController.state)
    }

    @Test
    fun testInventoryState() = withDefault { gameController, _ ->
        assertEquals(GameState.Default, gameController.state)
        gameController.openOrCloseInventory()
        assertEquals(GameState.Inventory, gameController.state)
        gameController.openOrCloseInventory()
        assertEquals(GameState.Default, gameController.state)
    }

    @Test
    fun testWall() = withDefault { gameController, idGenerator ->
        gameController.currentLevel.addEntity(Wall(GameObject(idGenerator.generate(), Position(1, 1), RectShape())))
        gameController.user.apply {
            assertTrue {
                moveTo(Position(0, 1))
            }
            assertFalse {
                moveTo(Position(1, 1))
            }
        }
    }

    @Test
    fun testPassive() = withDefault { gameController, idGenerator ->
        val monsterInfo = createMonsterInfo(idGenerator)
        val monster = createMonster(gameController, monsterInfo)
        val startPosition = monster.gameObject.position.clone()
        gameController.tick()
        assertEquals(startPosition, monster.gameObject.position)
    }

    private fun Position.sum(): Int = x + y

    @Test
    fun testAggressive() = withDefault { gameController, idGenerator ->
        val monsterInfo = createMonsterInfo(idGenerator, type = AggressiveMonsterType)
        val monster = createMonster(gameController, monsterInfo)
        val startPosition = monster.gameObject.position.clone()
        gameController.tick()
        assertEquals(startPosition.sum() - 1, monster.gameObject.position.sum())
    }

    @Test
    fun testCowardly() = withDefault { gameController, idGenerator ->
        val monsterInfo = createMonsterInfo(idGenerator, type = CowardlyMonsterType)
        val monster = createMonster(gameController, monsterInfo)
        val startPosition = monster.gameObject.position.clone()
        gameController.tick()
        assertEquals(startPosition.sum() + 1, monster.gameObject.position.sum())
    }

    @Test
    fun testAttack() = withDefault { gameController, idGenerator ->
        val monsterAttack = 10
        val monsterInfo = createMonsterInfo(
            idGenerator,
            position = Position(1, 0),
            characteristics = Characteristics(2 * userAttack, monsterAttack, 0)
        )
        val monster = createMonster(gameController, monsterInfo)
        gameController.user.moveTo(monster.gameObject.position)
        gameController.tick()
        assertEquals(userAttack, monster.characteristics.healthPoints)
        assertEquals(userHealth - monsterAttack, gameController.user.characteristics.healthPoints)
    }

    @Test
    fun testMonsterDeath() = withDefault { gameController, idGenerator ->
        val item = Armor
        val monsterInfo = createMonsterInfo(
            idGenerator,
            position = Position(1, 0),
            characteristics = Characteristics(userAttack, 0, 0),
            items = listOf(ItemWithAmount(item, 1))
        )
        val monster = createMonster(gameController, monsterInfo)
        assertEquals(0, gameController.user.userExperience.experiencePoints)

        gameController.user.moveTo(monster.gameObject.position)

        assertNull(gameController.currentLevel.findEntity(monster.id)) // monster is dead
        assertNotEquals(0, gameController.user.userExperience.experiencePoints) // + exp
        assertEquals(1, gameController.user.inventory.storage.getItemAmount(item)) // got item
    }

    @Test
    fun testBash() = withDefault(useBash = true) { gameController, idGenerator ->
        val monsterInfo = createMonsterInfo(
            idGenerator,
            position = Position(1, 0),
            characteristics = Characteristics(2 * userAttack, 0, 0),
            type = AggressiveMonsterType
        )
        val monster = createMonster(gameController, monsterInfo)
        val startPosition = monster.gameObject.position

        assertFalse(monster.isBashed())

        gameController.user.moveTo(monster.gameObject.position)

        assertTrue(monster.isBashed()) // is bashed
        val position = monster.gameObject.position
        assertNotEquals(startPosition, position) // move to another position
        gameController.tick()
        assertEquals(position, monster.gameObject.position) // didn't move
    }

    @Test
    fun testReplicant() = withDefault { gameController, idGenerator ->
        val monsterInfo = createMonsterInfo(
            idGenerator,
            type = ReplicableMonsterType(PassiveMonsterType)
        )
        createMonster(gameController, monsterInfo)
        val startCount = gameController.currentLevel.entities.size

        repeat(1000) { gameController.tick() }

        assertTrue(startCount < gameController.currentLevel.entities.size)
    }

    @Test
    fun testSmart() = withDefault { gameController, idGenerator ->
        val monsterInfo = createMonsterInfo(
            idGenerator,
            position = Position(0, 1),
            type = SmartMonsterType(userAttack, PassiveMonsterType),
            characteristics = Characteristics(3 * userAttack - 1, 0, 0)
        )
        val monster = createMonster(gameController, monsterInfo)
        val startPosition = monster.gameObject.position

        gameController.tick()
        assertEquals(startPosition, monster.gameObject.position) // stays

        gameController.user.moveTo(startPosition)
        gameController.tick()
        assertEquals(startPosition, monster.gameObject.position) // still staying

        gameController.user.moveTo(startPosition)
        gameController.tick()
        assertNotEquals(startPosition, monster.gameObject.position) // run
    }

}