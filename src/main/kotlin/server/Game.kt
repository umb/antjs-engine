package server

import engine.Engine

data class Game(val size: Int, val maxDuration: Int) {
    val id: String = Engine.guid()
    var tick = 0
}