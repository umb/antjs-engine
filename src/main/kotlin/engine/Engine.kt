package engine

import ant.PlayerScript
import engine.gameobjects.*
import engine.helpers.moveStraight
import engine.math.Vec2
import filehandling.FileLoader
import server.Game
import kotlin.math.floor

class Client(val id: String, val scriptPath: String?, val playerScript: PlayerScript) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class.js != other::class.js) return false

        other as Client

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}

class Engine(val game: Game) {
    val basepath = "/tmp/dynmic-js-test/clients"
    private val colonies: MutableMap<Client, AntColony> = mutableMapOf()
    private val gameState: GameState = GameState()
    val gamefieldsize: Int
        get() = game.size

    init {
        addBug()
    }

    companion object {
        @Suppress("DEPRECATION")
        fun random() = kotlin.js.Math.random()

        fun guid(): String {
            fun s4() = floor((1 + random()) * 0x1000).toInt().toString(16)
            return s4() + s4() + '-' + s4() + '-' + s4() + '-' + s4() + '-' + s4() + s4() + s4()

        }
    }

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
                patchClientAnt(client.playerScript, ant)
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
            ant.update(client.playerScript, gameState)
        } catch (e: Exception) {
            println("Error with client ${client.id}: $e")
        }

    }

    private fun patchClientAnt(player: PlayerScript?, ant: AntGameObject) {
        player?.asDynamic().moveStraight = { dist: Double -> moveStraight(ant, dist) }
    }

    fun cleanup() {

    }


}



