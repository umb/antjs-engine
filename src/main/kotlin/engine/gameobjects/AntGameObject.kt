package engine.gameobjects

import ant.PlayerScript
import engine.AntState
import engine.IdleState
import engine.math.Vec2
import server.Game


class AntGameObject(val playerId: String, var orientation: Double, id: String, position: Vec2) : GameObject(position, playerId) {

    companion object {
        val speed = 5.0
        val sightRange2: Double = 20.0 * 20.0
        val reach2: Double = 5.0 * 5.0
    }

    var state: AntState = IdleState()
        private set

    var carry: GameObject? = null


    var nextState: AntState = IdleState()

    fun update(playerScript: PlayerScript, gameState: GameState, game: Game) {
        state.update(playerScript, this, gameState, game)
        state = nextState
    }
}
