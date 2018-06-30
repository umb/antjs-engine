package engine.gameobjects

import engine.math.Vec2

class GameState(
    val sugar: MutableList<Sugar> = mutableListOf(),
    val apples: MutableList<Apple> = mutableListOf(),
    val bugs: MutableList<Bug> = mutableListOf(),
    val colonies: MutableList<AntColony> = mutableListOf()
) {

    val all: Sequence<GameObject>
        get() {
            val s = sugar.asSequence()
            val a = apples.asSequence()
            val b = bugs.asSequence()
            val c = colonies.asSequence()
            val ants = colonies.flatMap { it.ants }

            return s + a + b + c + ants
        }

    inline fun <reified T : GameObject> inRange(position: Vec2, distance2: Double) =
        all.filterIsInstance<T>().filter { position.dist2(it.position) <= distance2 }

    fun sugarInRange(position: Vec2, distance: Double) = sugar.asSequence().filter { position.dist2(it.position) <= distance }
    fun applesInRange(position: Vec2, distance: Double) = apples.asSequence().filter { position.dist2(it.position) <= distance }
    fun bugsInRange(position: Vec2, distance: Double) = bugs.asSequence().filter { position.dist2(it.position) <= distance }
    fun antsInRange(position: Vec2, distance: Double) = colonies.flatMap { it.ants }.asSequence().filter { position.dist2(it.position) <= distance }
    fun coloniesInRange(position: Vec2, distance: Double) = colonies.asSequence().filter { position.dist2(it.position) <= distance }

}
