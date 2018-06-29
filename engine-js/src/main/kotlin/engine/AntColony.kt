package engine

import engine.gameobjects.AntGameObject
import engine.gameobjects.GameObject
import engine.math.Vec2


class AntColony(playerId: String, position: Vec2) : GameObject(position, playerId) {
    companion object {
        const val INITIAL_ANT_COUNT = 10
    }

    val playerId: String
        get() = id

    val ants: MutableList<AntGameObject> = mutableListOf()

    init {
        for (i in 1..INITIAL_ANT_COUNT) {
            val orientation = Engine.random() * 2 * kotlin.math.PI
            ants.add(AntGameObject(playerId, orientation, playerId + "_" + i, position))
        }
    }
}