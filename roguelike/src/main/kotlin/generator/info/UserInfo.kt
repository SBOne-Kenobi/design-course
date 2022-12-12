package generator.info

import engine.GameObject
import generator.Characteristics
import inventory.items.ItemWithAmount

/**
 * Info for user generation.
 */
data class UserInfo(
    val characteristics: Characteristics,
    val items: List<ItemWithAmount>,
    val gameObject: GameObject
) : GenerationInfo
