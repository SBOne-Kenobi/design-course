package launcher

import engine.GameEngine
import engine.GameScene
import entity.GameMap
import entity.Level
import entity.models.Chest
import entity.models.Entity
import entity.models.Monster
import entity.models.User
import entity.models.Wall
import generator.GameMapInfo
import generator.LevelInfo
import generator.info.ChestInfo
import generator.info.GenerationInfo
import generator.info.MonsterInfo
import generator.info.UserInfo
import generator.info.WallInfo
import inventory.containers.DefaultContainer

class InfoTranslator(
   private val engine: GameEngine
) {
    fun translate(gameMapInfo: GameMapInfo): GameMap =
        GameMap(
            gameMapInfo.levels.map(::translate)
        )

    private fun translate(levelInfo: LevelInfo): Level {
        val scene = GameScene()
        val entities = levelInfo.info.mapNotNull(::translate)
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

    private fun translate(info: GenerationInfo): Entity? = when (info) {
        is WallInfo -> Wall(info.gameObject)
        is ChestInfo -> Chest(info.gameObject, DefaultContainer(info.items))
        is UserInfo -> User(info.gameObject, info.characteristics, engine).apply {
            info.items.forEach { (item, amount) ->
                inventory.storage.addItem(item, amount)
            }
        }
        is MonsterInfo -> Monster(info.gameObject, info.characteristics, info.type).apply {
            info.items.forEach { (item, amount) ->
                items.addItem(item, amount)
            }
        }
        else -> null
    }
}