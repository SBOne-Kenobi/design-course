package generator.info

import engine.GameObject
import inventory.items.ItemWithAmount

data class ChestInfo(
    override val items: List<ItemWithAmount>,
    val gameObject: GameObject
) : GenerationInfoWithItems
