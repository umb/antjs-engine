package engine.gameobjects

import ant.PlayerScript
import engine.math.Vec2

sealed class AntState {
    abstract fun update(playerScript: PlayerScript, ant: AntGameObject, gameState: GameState)
}

class IdleState : AntState() {
    override fun update(playerScript: PlayerScript, ant: AntGameObject, gameState: GameState) {
        playerScript.idle()
    }
}

class Moving(val target: Vec2?) : AntState() {
    override fun update(playerScript: PlayerScript, ant: AntGameObject, gameState: GameState) {

        val update = Vec2.ofAngle(ant.orientation) * AntGameObject.speed
        ant.position += update

        if (target != null) {
            // TODO check if we have arrived and stop if we have
        }

        // TODO scan around our position to discover hostile gameobjects and food
    }
}
