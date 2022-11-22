package generator.info

import engine.GameObject
import generator.Characteristics
import inventory.items.ItemWithAmount

data class UserInfo(
    override val characteristics: Characteristics,
    override val items: List<ItemWithAmount>,
    val gameObject: GameObject
) : GenerationInfoWithCharacteristics, GenerationInfoWithItems
