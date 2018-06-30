package server

data class Game(val id: String, val size: Int, val maxDuration: Int) {
    var tick = 0
}