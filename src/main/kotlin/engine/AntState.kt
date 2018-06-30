package engine

import ant.PlayerScript
import engine.gameobjects.AntGameObject
import engine.gameobjects.GameState
import engine.gameobjects.Sugar
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
            // TODO check if we have arrived and stop if we have arrived
        }

        // TODO check for out of bounds
        // TODO scan around our position to discover hostile gameobjects and food

        val appleInSight = gameState.applesInRange(ant.position, AntGameObject.sightRange2).firstOrNull()
        if (appleInSight != null && playerScript.asDynamic().seeApple != null) {
            playerScript.seeApple(appleInSight)
        }

        val sugarInSight = gameState.inRange<Sugar>(ant.position, AntGameObject.sightRange2).firstOrNull()
        if (sugarInSight != null && playerScript.asDynamic().seeSugar != null) {
            playerScript.seeSugar(sugarInSight)
        }

    }
}
