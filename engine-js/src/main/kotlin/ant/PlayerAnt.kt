package ant

class GameObject

class Sugar
class Apple
data class Ant(val playerId: String)
class Bug

interface PlayerAnt {

    // basic
    fun idle()

    // food

    fun seeSugar(sugar: Sugar)
    fun seeApple(apple: Apple)
    fun arriveAtSugar(sugar: Sugar)
    fun arriveAtApple(apple: Apple)

    // Combat

    fun seeAnt(ant: Ant)
    fun seeBug(bug: Bug)
    fun hasDied()
    fun arriveAtAnt(ant: Ant)
    fun arriveAtBug(bug: Bug)

}


interface Helper {
    fun moveTo(gameObject: GameObject)
    fun pickup(gameObject: GameObject)

}