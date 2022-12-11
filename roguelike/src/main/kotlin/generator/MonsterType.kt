package generator

/**
 * The type of monster.
 */
sealed interface MonsterType {
    val description: String
    val initCharacteristics: Characteristics
    val experiencePoints: Int
}

object PassiveMonsterType : MonsterType {
    override val description: String = "Passive monster"

    override val initCharacteristics: Characteristics = Characteristics(
        healthPoints = 100,
        attackPoints = 50,
        protectionPoints = 0,
    )

    override val experiencePoints: Int = 20
}

object AggressiveMonsterType : MonsterType {
    override val description: String = "Aggressive monster"

    override val initCharacteristics: Characteristics = Characteristics(
        healthPoints = 50,
        attackPoints = 50,
        protectionPoints = 10,
    )

    override val experiencePoints: Int = 50
}

object CowardlyMonsterType : MonsterType {
    override val description: String = "Cowardly monster"

    override val initCharacteristics: Characteristics = Characteristics(
        healthPoints = 20,
        attackPoints = 10,
        protectionPoints = 0,
    )

    override val experiencePoints: Int = 40
}
