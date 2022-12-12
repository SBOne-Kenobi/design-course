package generator.info

import engine.GameObject
import inventory.items.ItemWithAmount

/**
 * Info for chest generation.
 */
data class ChestInfo(
    val items: List<ItemWithAmount>,
    val gameObject: GameObject
) : GenerationInfo
