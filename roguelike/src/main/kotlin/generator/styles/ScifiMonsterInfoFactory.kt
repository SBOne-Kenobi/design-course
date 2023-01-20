package generator.styles

import engine.GameObject
import generator.AggressiveMonsterType
import generator.Characteristics
import generator.CowardlyMonsterType
import generator.PassiveMonsterType
import generator.ScifiStyle
import generator.info.MonsterInfo
import inventory.items.ItemWithAmount

class ScifiMonsterInfoFactory : AbstractMonsterInfoFactory() {
    override fun createAggressive(
        gameObject: GameObject,
        characteristics: Characteristics,
        items: List<ItemWithAmount>
    ): MonsterInfo = MonsterInfo(
        name = "Crazy cyborg",
        style = ScifiStyle,
        type = AggressiveMonsterType,
        gameObject, characteristics, items
    )

    override fun createCowardly(
        gameObject: GameObject,
        characteristics: Characteristics,
        items: List<ItemWithAmount>
    ): MonsterInfo = MonsterInfo(
        name = "Robo-mouse",
        style = ScifiStyle,
        type = CowardlyMonsterType,
        gameObject, characteristics, items
    )

    override fun createPassive(
        gameObject: GameObject,
        characteristics: Characteristics,
        items: List<ItemWithAmount>
    ): MonsterInfo = MonsterInfo(
        name = "Skyscraper",
        style = ScifiStyle,
        type = PassiveMonsterType,
        gameObject, characteristics, items
    )
}