package engine

import ant.PlayerScript
import engine.gameobjects.*
import engine.helpers.*
import engine.math.Vec2
import filehandling.FileLoader
import server.Game
import kotlin.math.floor


class Engine(val game: Game) {
    val basepath = "/tmp/dynmic-js-test/clients"
    private val colonies: MutableMap<Client, AntColony> = mutableMapOf()
    private val gameState: GameState = GameState()
    val gamefieldsize: Int
        get() = game.size

    init {
        addBug()
    }

    var running = false


    fun loadClientDirect(clientId: String, code: String) {

        println("loading client $clientId with inline script")

        val client = Client(clientId, null, FileLoader.loadClientCodeDirect(code)!!)

        colonies.getOrPut(client) {
            val position = Vec2.randomScaled(gamefieldsize)
            val colony = AntColony(clientId, position)
            colonyAdded(colony)
            colony
        }

    }


    private fun colonyAdded(colony: AntColony) {
        for (i in 1..5) {
            gameState.sugar.add(Sugar(Vec2.randomScaled(gamefieldsize), guid()))

        }

        for (i in 1..2) {
            gameState.apples.add(Apple(Vec2.randomScaled(gamefieldsize), guid()))

        }

        gameState.colonies.add(colony)
    }


    fun addBug() {
        gameState.bugs.add(Bug(Vec2.randomScaled(gamefieldsize), guid()))
    }

    fun simulate(): GameState {
        game.tick++
        for ((client, colony) in colonies) {
            for (ant in colony.ants) {
                patchClientAnt(client.playerScript, ant, colony)
                step(client, gameState, ant)
            }
        }

        //val saved = JSON.stringify(gameState)
        // FileLoader.savelog("$basepath/../log", game.tick, saved)

        return gameState

    }

    fun addPlayer(id: String, code: String) {
        //FileLoader.putCode(basepath, id, code)
        //loadClients()
        loadClientDirect(id, code)
    }

    private fun step(client: Client, gameState: GameState, ant: AntGameObject) {
        try {
            ant.update(client.playerScript, gameState, game)
        } catch (e: Exception) {
            println("Error with client ${client.id}: $e")
        }

    }

    /**
     * Patch client script dynamically so the functions are there and we later know which ant was meant
     * it is really hacky
     */
    private fun patchClientAnt(player: PlayerScript?, ant: AntGameObject, colony: AntColony) {
        // FIXME reference to the actual gameObject allows for all kinds of shenanigans, we only use position anyways so copy

        player?.asDynamic().moveStraight = { dist: Double -> moveStraight(ant, dist) }
        player?.asDynamic().stop = { stop(ant) }
        player?.asDynamic().moveTo = { target: GameObject -> moveTo(ant, target) }
        player?.asDynamic().goHome = { goHome(ant, colony) }

        // food
        player?.asDynamic().pickup = { target: GameObject -> pickup(ant, target) }
        player?.asDynamic().carry = ant.carry
    }

    fun cleanup() {

    }

    companion object {
        @Suppress("DEPRECATION")
        fun random() = kotlin.js.Math.random()

        fun guid(): String {
            fun s4() = floor((1 + random()) * 0x1000).toInt().toString(16)
            return s4() + s4() + '-' + s4() + '-' + s4() + '-' + s4() + '-' + s4() + s4() + s4()

        }
    }

}



