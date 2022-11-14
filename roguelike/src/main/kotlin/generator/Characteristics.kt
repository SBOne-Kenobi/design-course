package generator

data class Characteristics(
    private var health: Double = 100.0,
    private var attack: Double = 5.0,
    private var protection: Double = 5.0,
) {
    fun getHealth(): Double = health

    fun getAttack(): Double = attack

    fun getProtection(): Double = protection

    fun shiftHealth(shift: Double) {
        setHealth(health + shift)
    }

    fun shiftAttack(shift: Double) {
        setAttack(attack + shift)
    }

    fun shiftProtection(shift: Double) {
        setProtection(protection + shift)
    }

    fun setHealth(newValue: Double) {
        health = newValue
            .coerceAtLeast(0.0)
            .coerceAtMost(100.0)
    }

    fun setAttack(newValue: Double) {
        attack = newValue
            .coerceAtLeast(0.0)
            .coerceAtMost(100.0)
    }

    fun setProtection(newValue: Double) {
        protection = newValue
            .coerceAtLeast(0.0)
            .coerceAtMost(100.0)
    }
}