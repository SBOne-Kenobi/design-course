package generator.info

import engine.GameObject
import generator.Characteristics
import inventory.items.Item

class UserInfo : GenerationInfoWithCharacteristics, GenerationInfoWithItems {
    override val characteristics: Characteristics
        get() = TODO("Not yet implemented")
    override val items: List<Item>
        get() = TODO("Not yet implemented")
    override val gameObject: GameObject
        get() = TODO("Not yet implemented")
}