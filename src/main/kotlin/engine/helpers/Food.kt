package engine.helpers

import engine.gameobjects.AntGameObject
import engine.gameobjects.Apple
import engine.gameobjects.GameObject
import engine.gameobjects.Sugar

fun pickup(antGameObject: AntGameObject, gameObject: GameObject?) {

    when (gameObject) {
        is Sugar, is Apple -> {
            if (antGameObject.position.dist2(gameObject.position) <= AntGameObject.reach2) {
                antGameObject.carry = gameObject
            } else {
                println("$antGameObject would love to pick up $gameObject but it is too far away")
            }
        }

        else -> println("$antGameObject cannot pickup $gameObject")

    }

}
