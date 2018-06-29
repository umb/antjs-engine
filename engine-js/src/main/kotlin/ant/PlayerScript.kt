package ant

import engine.gameobjects.*


interface PlayerScript {

    // basic
    fun idle()

    // food

    fun seeSugar(sugar: Sugar)
    fun seeApple(apple: Apple)
    fun arriveAtSugar(sugar: Sugar)
    fun arriveAtApple(apple: Apple)

    // Combat

    fun seeAnt(ant: AntGameObject)
    fun seeBug(bug: Bug)
    fun hasDied()
    fun arriveAtAnt(ant: AntGameObject)
    fun arriveAtBug(bug: Bug)

}


interface Helper {
    fun moveTo(gameObject: GameObject)
    fun pickup(gameObject: GameObject)

}