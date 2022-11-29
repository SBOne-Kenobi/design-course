package generator

/**
 * The type of monster.
 */
sealed interface MonsterType {
    val description: String
    val initCharacteristics: Characteristics
}

class PassiveMonsterType : MonsterType {
    override val description: String = "Passive monster"
    override val initCharacteristics: Characteristics = Characteristics(
        healthPoints = 100,
        attackPoints = 50,
        protectionPoints = 0,
    )
}
