package entity

data class GameMap(val levels: List<Level>) {
    fun getNextLevel(level: Level): Level? {
        TODO()
    }

    fun getPrevLevel(level: Level): Level? {
        TODO()
    }
}