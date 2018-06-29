package engine

import ant.PlayerScript
import engine.gameobjects.*
import engine.helpers.moveStraight
import engine.math.Vec2
import filehandling.FileLoader
import kotlin.math.floor

class Client(val id: String, val scriptPath: String, val playerScript: PlayerScript)

object Engine {
    val gamefieldsize = 100
    val basepath = "/Users/rick/Documents/dynmic-js-test/clients"


    private val colonies: MutableMap<Client, AntColony> = mutableMapOf()

    private val gameState: GameState = GameState()

    init {
        addBug()
    }

    suspend fun loadClients() {
        val clientDirectories = FileLoader.walkDir(basepath)
        for (dir in clientDirectories) {
            val clientId = dir.split("/").last()
            val scriptPath = "$dir/ant.js"
            println("loading client $clientId with script $scriptPath")

            val client = Client(clientId, scriptPath, FileLoader.loadClientCode(scriptPath)!!)

            colonies.getOrPut(client) {
                val position = Vec2.randomScaled(gamefieldsize)
                val colony = AntColony(clientId, position)
                colonyAdded(colony)
                colony
            }
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


    fun guid(): String {

        fun s4() = floor((1 + random()) * 0x1000).toString().substring(1)


        /*
        function s4() {
            return Math.floor((1 + Math.random()) * 0x10000)
                .toString(16)
                .substring(1);
        }
        */
        return s4() + s4() + '-' + s4() + '-' + s4() + '-' + s4() + '-' + s4() + s4() + s4();

    }


    fun addBug() {
        gameState.bugs.add(Bug(Vec2.randomScaled(gamefieldsize), guid()))
    }

    var tick = 0
    suspend fun simulate() {
        tick++
        for ((client, colony) in colonies) {
            for (ant in colony.ants) {
                patchClientAnt(client.playerScript, ant)
                step(client, gameState, ant)
            }
        }

        // save game state
        val saved = JSON.stringify(gameState)
        FileLoader.savelog("$basepath/../log", tick, saved)
    }

    @Suppress("DEPRECATION")
    fun random() = kotlin.js.Math.random()

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


}



