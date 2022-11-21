package generator.info

import inventory.items.ItemWithAmount

interface GenerationInfoWithItems : GenerationInfo {
    val items: List<ItemWithAmount>
}