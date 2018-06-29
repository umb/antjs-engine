package engine

import engine.gameobjects.AntGameObject
import engine.gameobjects.Position


class AntColony(val playerId: String, val position: Position) {
    companion object {
        const val INITIAL_ANT_COUNT = 10
    }

    val ants: MutableList<AntGameObject> = mutableListOf()

    init {
        for (i in 1..INITIAL_ANT_COUNT) {
            val orientation = Engine.random()
            ants.add(AntGameObject(playerId, playerId + "_" + i, orientation, position))
        }
    }
}