package generator.info

import inventory.items.Item

interface GenerationInfoWithItems : GenerationInfo {
    val items: List<Item>
}