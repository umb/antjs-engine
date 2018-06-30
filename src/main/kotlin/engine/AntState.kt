package engine

import ant.PlayerScript
import engine.gameobjects.AntGameObject
import engine.gameobjects.GameState
import engine.gameobjects.Sugar
import engine.math.Vec2
import server.Game

sealed class AntState {
    abstract fun update(playerScript: PlayerScript, ant: AntGameObject, gameState: GameState, game: Game)
}

class IdleState : AntState() {
    override fun update(playerScript: PlayerScript, ant: AntGameObject, gameState: GameState, game: Game) {
        playerScript.idle()
    }
}

class Moving(val target: Vec2?) : AntState() {
    override fun update(playerScript: PlayerScript, ant: AntGameObject, gameState: GameState, game: Game) {

        val update = Vec2.ofAngle(ant.orientation) * AntGameObject.speed
        ant.position += update

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


        if (target != null) {
            if (ant.position.dist2(target) < AntGameObject.reach2) {
                val appleInReach = gameState.applesInRange(ant.position, AntGameObject.reach2).firstOrNull()
                if (appleInReach != null) {
                    playerScript.arriveAtApple(appleInReach)
                }

                val sugarInReach = gameState.sugarInRange(ant.position, AntGameObject.reach2).firstOrNull()
                if (sugarInReach != null) {
                    playerScript.arriveAtSugar(sugarInReach)
                }
            }
        }


        if (ant.position.x < 0) {
            ant.position.x = 0.0
            ant.orientation = kotlin.math.PI - ant.orientation
        }

        if (ant.position.x > game.size) {
            ant.position.x = game.size.toDouble()
            ant.orientation = kotlin.math.PI - ant.orientation

        }

        if (ant.position.y < 0) {
            ant.position.y = 0.0
            ant.orientation = -ant.orientation % 2 * kotlin.math.PI
        }

        if (ant.position.y > game.size) {
            ant.position.y = game.size.toDouble()
            ant.orientation = -ant.orientation % 2 * kotlin.math.PI
        }
    }
}
