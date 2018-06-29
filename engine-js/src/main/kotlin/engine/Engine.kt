package engine

import ant.PlayerScript
import engine.gameobjects.AntGameObject
import engine.gameobjects.Position
import filehandling.FileLoader

class Client(val id: String, val scriptPath: String, val playerScript: PlayerScript?)

object Engine {
    private val clients: MutableList<Client> = mutableListOf()

    val gamefieldsize = 100

    suspend fun loadClients() {
        val basepath = "/Users/rick/Documents/dynmic-js-test/clients"
        val clientDirectories = FileLoader.walkDir(basepath)
        for (dir in clientDirectories) {
            val clientId = dir.split("/").last()
            val scriptPath = "$dir/ant.js"

            println("loading client $clientId with script $scriptPath")

            val client = Client(clientId, scriptPath, FileLoader.loadClientCode(scriptPath))



            colonies.getOrPut(client) {
                val position = Position.randomScaled(gamefieldsize)
                AntColony(clientId, position)
            }

            clients.add(client)
        }
    }


    private val colonies: MutableMap<Client, AntColony> = mutableMapOf()


    fun simulate() {
        for (client in clients) {
            val colony = colonies.getValue(client)
            for (ant in colony.ants) {
                patchClientAnt(client.playerScript, ant)
                step(client)

            }
        }
    }

    @Suppress("DEPRECATION")
    fun random() = kotlin.js.Math.random()

    private fun step(client: Client) {
        try {
            client.playerScript?.idle()
        } catch (e: Exception) {
            console.log("Error with client ${client.id}: $e")
        }

    }

    private fun patchClientAnt(player: PlayerScript?, ant: AntGameObject) {
        player?.asDynamic().moveStraight = { dist: Int -> moveStraight(ant, dist) }
    }


}



