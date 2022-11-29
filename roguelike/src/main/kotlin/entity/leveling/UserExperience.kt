package entity.leveling

class UserExperience {
    var level: Int = 1
        private set
    var experiencePoints: Int = 0
        private set
    var experiencePointsInCurrentLevel: Int = 100
        private set

    fun addExp(value: Int) {
        var remain = value
        while (remain > 0) {
            if (experiencePoints + remain < experiencePointsInCurrentLevel) {
                experiencePoints += remain
                remain = 0
            } else {
                remain -= (experiencePointsInCurrentLevel - experiencePoints)
                levelUp()
            }
        }
    }

    private fun levelUp() {
        experiencePoints = 0
        level += 1
        experiencePointsInCurrentLevel += 100
    }

}