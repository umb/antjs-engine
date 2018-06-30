package engine.helpers

import engine.gameobjects.AntGameObject
import engine.gameobjects.Apple
import engine.gameobjects.GameObject
import engine.gameobjects.Sugar

fun pickup(antGameObject: AntGameObject, gameObject: GameObject?) {

    when (gameObject) {
        is Sugar, is Apple -> antGameObject.carry = gameObject
        else -> println("$antGameObject cannot pickup $gameObject")

    }

}
