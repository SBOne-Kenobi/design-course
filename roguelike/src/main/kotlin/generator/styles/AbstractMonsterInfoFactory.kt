package generator.styles

import engine.GameObject
import generator.AggressiveMonsterType
import generator.Characteristics
import generator.CowardlyMonsterType
import generator.MonsterType
import generator.PassiveMonsterType
import generator.ReplicableMonsterType
import generator.SmartMonsterType
import generator.info.MonsterInfo
import inventory.items.ItemWithAmount

abstract class AbstractMonsterInfoFactory {
    fun createMonsterInfo(
        type: MonsterType,
        gameObject: GameObject,
        characteristics: Characteristics,
        items: List<ItemWithAmount>,
    ): MonsterInfo = when (type) {
        is AggressiveMonsterType -> createAggressive(gameObject, characteristics, items)
        is CowardlyMonsterType -> createCowardly(gameObject, characteristics, items)
        is PassiveMonsterType -> createPassive(gameObject, characteristics, items)
        is ReplicableMonsterType -> {
            val baseInfo = createMonsterInfo(type.baseType, gameObject, characteristics, items)
            baseInfo.copy(type = type)
        }
        is SmartMonsterType -> {
            val baseInfo = createMonsterInfo(type.baseType, gameObject, characteristics, items)
            baseInfo.copy(type = type)
        }
    }

    protected abstract fun createAggressive(
        gameObject: GameObject,
        characteristics: Characteristics,
        items: List<ItemWithAmount>,
    ): MonsterInfo


    protected abstract fun createCowardly(
        gameObject: GameObject,
        characteristics: Characteristics,
        items: List<ItemWithAmount>,
    ): MonsterInfo


    protected abstract fun createPassive(
        gameObject: GameObject,
        characteristics: Characteristics,
        items: List<ItemWithAmount>,
    ): MonsterInfo
}