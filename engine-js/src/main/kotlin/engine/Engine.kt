package engine

import ant.Ant
import ant.PlayerAnt
import filehandling.FileLoader

class Client(val id: String, val scriptPath: String, val ant: PlayerAnt?)

object Engine {
    private val clients: MutableList<Client> = mutableListOf()

    suspend fun loadClients() {
        val basepath = "/Users/rick/Documents/dynmic-js-test/clients"
        val clientDirectories = FileLoader.walkDir(basepath)
        for (dir in clientDirectories) {
            val clientId = dir.split("/").last()
            val scriptPath = "$dir/ant.js"

            println("loading client $clientId with script $scriptPath")

            val client = Client(clientId, scriptPath, FileLoader.loadClientCode(scriptPath))

            //TODO this is hacky
            // client.ant.asDynamic()["moveStraight"] = 1

            val ant: Ant = Ant(clientId)

            patchClientAnt(client.ant, ant)


            clients.add(client)
        }
    }

    fun simulate() {
        for (client in clients) {
            step(client)
        }
    }

    private fun step(client: Client) {
        try {
            client.ant?.idle()
        } catch (e: Exception) {
            console.log("Error with client ${client.id}: $e")
        }

    }

    private fun patchClientAnt(player: PlayerAnt?, ant: Ant) {
        player?.asDynamic().moveStraight = { dist: Int -> moveStraight(ant, dist) }
    }


}



