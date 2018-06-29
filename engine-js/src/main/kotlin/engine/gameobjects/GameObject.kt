package engine.gameobjects

import engine.Engine

data class Position(var x: Double, var y: Double) {
    companion object {
        fun randomScaled(scale: Int) = Position(Engine.random() * scale, Engine.random() * scale)
    }
}

abstract class GameObject(val position: Position)

class Sugar(position: Position) : GameObject(position)
class Apple(position: Position) : GameObject(position)
class Bug(position: Position) : GameObject(position)
