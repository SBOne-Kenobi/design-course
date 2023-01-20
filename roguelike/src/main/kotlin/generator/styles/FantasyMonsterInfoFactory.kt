package generator.styles

import engine.GameObject
import generator.AggressiveMonsterType
import generator.Characteristics
import generator.CowardlyMonsterType
import generator.FantasyStyle
import generator.PassiveMonsterType
import generator.info.MonsterInfo
import inventory.items.ItemWithAmount

class FantasyMonsterInfoFactory : AbstractMonsterInfoFactory() {
    override fun createAggressive(
        gameObject: GameObject,
        characteristics: Characteristics,
        items: List<ItemWithAmount>
    ): MonsterInfo = MonsterInfo(
        name = "Dragon",
        style = FantasyStyle,
        type = AggressiveMonsterType,
        gameObject, characteristics, items
    )

    override fun createCowardly(
        gameObject: GameObject,
        characteristics: Characteristics,
        items: List<ItemWithAmount>
    ): MonsterInfo = MonsterInfo(
        name = "Slime",
        style = FantasyStyle,
        type = CowardlyMonsterType,
        gameObject, characteristics, items
    )

    override fun createPassive(
        gameObject: GameObject,
        characteristics: Characteristics,
        items: List<ItemWithAmount>
    ): MonsterInfo = MonsterInfo(
        name = "Evil oak",
        style = FantasyStyle,
        type = PassiveMonsterType,
        gameObject, characteristics, items
    )
}