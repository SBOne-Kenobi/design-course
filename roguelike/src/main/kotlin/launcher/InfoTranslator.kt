package launcher

import engine.GameEngine
import engine.GameScene
import entity.GameController
import entity.GameMap
import entity.Level
import entity.models.Chest
import entity.models.Entity
import entity.models.Monster
import entity.models.User
import entity.models.Wall
import entity.models.monsters.MonsterStrategyFactory
import generator.info.ChestInfo
import generator.info.GameMapInfo
import generator.info.GenerationInfo
import generator.info.LevelInfo
import generator.info.MonsterInfo
import generator.info.UserInfo
import generator.info.WallInfo
import inventory.containers.DefaultContainer
import inventory.grimoire.ItemRecipe

/**
 * Class that creates entities from generation info.
 */
class InfoTranslator(
   private val engine: GameEngine,
   private val gameController: GameController,
   private val monsterStrategyFactory: MonsterStrategyFactory,
) {

    /**
     * Translate [GameMapInfo] to [GameMap].
     */
    fun translate(gameMapInfo: GameMapInfo): GameMap =
        GameMap(
            gameMapInfo.levels.map(::translate)
        )

    private fun translate(levelInfo: LevelInfo): Level {
        val scene = GameScene()
        val entities = levelInfo.info.mapNotNull(::translateEntity)
        entities.forEach {
            scene.registerObject(it.gameObject)
        }

        return Level(
            levelInfo.name,
            levelInfo.description,
            scene,
            entities
        )
    }

    private fun translateEntity(info: GenerationInfo): Entity? = when (info) {
        is WallInfo -> Wall(info.gameObject)
        is ChestInfo -> Chest(info.gameObject, DefaultContainer(info.items))
        is UserInfo -> User(info.gameObject, info.characteristics, engine, gameController).apply {
            info.items.forEach { (item, amount) ->
                if (item is ItemRecipe) {
                    inventory.grimoire.recipes.add(item)
                } else {
                    inventory.storage.addItem(item, amount)
                }
            }
        }
        is MonsterInfo -> Monster(
            info.gameObject,
            info.characteristics,
            DefaultContainer(info.items),
            info.type,
            monsterStrategyFactory.getStrategy(info.type, info.gameObject, gameController),
            engine,
            gameController
        )
        else -> null
    }
}