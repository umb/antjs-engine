package engine.gameobjects

import ant.PlayerScript
import engine.math.Vec2

sealed class AntState {
    abstract fun update(playerScript: PlayerScript)
}

class IdleState : AntState() {
    override fun update(playerScript: PlayerScript) {
        playerScript.idle()
    }
}

class Moving(val target: Vec2?) : AntState() {
    override fun update(playerScript: PlayerScript) {


    }
}
