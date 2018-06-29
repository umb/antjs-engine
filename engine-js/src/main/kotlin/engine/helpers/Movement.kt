package engine.helpers

import engine.gameobjects.AntGameObject
import engine.gameobjects.GameObject
import engine.gameobjects.Moving
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


fun moveTo(gameObject: GameObject) {

}
