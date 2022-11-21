package generator.info

import engine.GameObject
import generator.Characteristics
import generator.MonsterType
import inventory.items.ItemWithAmount

data class MonsterInfo(
    val type: MonsterType,
    override val gameObject: GameObject,
    override val characteristics: Characteristics,
    override val items: List<ItemWithAmount>
) : GenerationInfoWithCharacteristics, GenerationInfoWithItems
