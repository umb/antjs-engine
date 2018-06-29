package engine.gameobjects

class GameState(
    val sugar: MutableList<Sugar> = mutableListOf(),
    val apples: MutableList<Apple> = mutableListOf(),
    val bugs: MutableList<Bug> = mutableListOf(),
    val colonies: MutableList<AntColony> = mutableListOf()
)