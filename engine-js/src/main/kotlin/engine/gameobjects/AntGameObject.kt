package engine.gameobjects

import ant.PlayerScript
import engine.math.Vec2


class AntGameObject(val playerId: String, val orientation: Double, id: String, position: Vec2) : GameObject(position, playerId) {

    companion object {
        val speed = 5.0
    }

    var state: AntState = IdleState()
        private set


    var nextState: AntState = IdleState()

    fun update(playerScript: PlayerScript, gameState: GameState) {
        state.update(playerScript, this, gameState)
        state = nextState
    }
}
