package generator.info

import engine.GameObject
import generator.Characteristics
import generator.MonsterType
import inventory.items.Item

class MonsterInfo(val type: MonsterType): GenerationInfoWithCharacteristics, GenerationInfoWithItems {
    override val gameObject: GameObject
        get() = TODO("Not yet implemented")
    override val characteristics: Characteristics
        get() = TODO("Not yet implemented")
    override val items: List<Item>
        get() = TODO("Not yet implemented")
}
