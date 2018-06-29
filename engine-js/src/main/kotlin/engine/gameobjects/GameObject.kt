package engine.gameobjects

import engine.AntColony
import engine.math.Vec2

abstract class GameObject(var position: Vec2, val id: String)

class GameState(
    val sugar: MutableList<Sugar> = mutableListOf(),
    val apples: MutableList<Apple> = mutableListOf(),
    val bugs: MutableList<Bug> = mutableListOf(),
    val colonies: MutableList<AntColony> = mutableListOf()
)