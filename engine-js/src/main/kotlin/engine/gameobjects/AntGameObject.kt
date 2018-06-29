package engine.gameobjects

import ant.PlayerScript
import engine.math.Vec2


class AntGameObject(val playerId: String, val orientation: Double, id: String, position: Vec2) : GameObject(position, playerId) {

    var state: AntState = IdleState()
        private set

    var nextState: AntState = IdleState()

    fun update(playerScript: PlayerScript) {
        state.update(playerScript)
        state = nextState
    }
}
