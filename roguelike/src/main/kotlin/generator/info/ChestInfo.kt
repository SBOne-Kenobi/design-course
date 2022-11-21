package generator.info

import engine.GameObject
import inventory.items.ItemWithAmount

data class ChestInfo(
    override val items: List<ItemWithAmount>,
    override val gameObject: GameObject
) : GenerationInfoWithItems
