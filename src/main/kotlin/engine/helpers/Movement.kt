package engine.helpers

import engine.IdleState
import engine.Moving
import engine.gameobjects.AntGameObject
import engine.gameobjects.GameObject
import engine.math.Vec2


fun moveStraight(ant: AntGameObject, distance: Double? = null) {
    println("$ant moveStraight $distance")

    if (distance != null) {
        val lookDirection = Vec2.ofAngle(ant.orientation)
        ant.nextState = Moving(ant.position + lookDirection * distance)
    } else {
        ant.nextState = Moving(null)
    }
}


fun moveTo(ant: AntGameObject, gameObject: GameObject) {
    println("$ant moveTo ${gameObject.position}")

    ant.nextState = Moving(gameObject.position)
}

fun stop(ant: AntGameObject) {
    ant.nextState = IdleState()
}