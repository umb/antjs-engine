package engine

import engine.gameobjects.AntGameObject


class AntColony(val playerId: String) {
    companion object {
        const val INITIAL_ANT_COUNT = 10
    }

    val ants: MutableList<AntGameObject> = mutableListOf()

    init {
        for (i in 1..INITIAL_ANT_COUNT) {
            ants.add(AntGameObject(playerId, playerId + "_" + i))
        }
    }
}