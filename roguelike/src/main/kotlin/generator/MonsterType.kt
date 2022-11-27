package generator

/**
 * The type of monster.
 */
interface MonsterType {
    val description: String
    val initHealthPoints: Int
    val initAttackPoints: Int
    val initProtectionPoints: Int
}