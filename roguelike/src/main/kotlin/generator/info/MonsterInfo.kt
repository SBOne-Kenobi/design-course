package generator.info

import engine.GameObject
import generator.Characteristics
import generator.MonsterStyle
import generator.MonsterType
import inventory.items.ItemWithAmount

/**
 * Info for monster generation.
 */
data class MonsterInfo(
    val name: String,
    val style: MonsterStyle,
    val type: MonsterType,
    val gameObject: GameObject,
    val characteristics: Characteristics,
    val items: List<ItemWithAmount>
) : GenerationInfo
