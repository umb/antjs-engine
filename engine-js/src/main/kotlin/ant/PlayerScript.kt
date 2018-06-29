package ant

import engine.gameobjects.AntGameObject
import engine.gameobjects.Apple
import engine.gameobjects.Bug
import engine.gameobjects.Sugar


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