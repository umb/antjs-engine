package engine

import ant.Ant
import ant.PlayerAnt
import filehandling.FileLoader

class Client(val id: String, val scriptPath: String, val ant: PlayerAnt?)

external fun require(path: String): Any?


object Engine {
    private val clients: MutableList<Client> = mutableListOf()

    fun loadClientES2015(path: String): PlayerAnt? {
        val moduleHolder = require(path)
        return moduleHolder?.unsafeCast<PlayerAnt>()
    }

    suspend fun loadClients() {
        val basepath = "/Users/rick/Documents/dynmic-js-test/clients"
        val clientDirectories = FileLoader.walkDir(basepath)
        for (dir in clientDirectories) {
            val clientId = dir.split("/").last()
            val scriptPath = "$dir/ant.js"

            println("loading client $clientId with script $scriptPath")

            val client = Client(clientId, scriptPath, loadClientES2015(scriptPath))

            //TODO this is hacky
            // client.ant.asDynamic()["moveStraight"] = 1

            val ant: Ant = Ant(clientId)

            client.ant.asDynamic().moveStraight = { dist: Int -> moveStraight(ant, dist) }


            clients.add(client)
        }
    }

    fun simulate() {
        for (client in clients) {
            step(client)
        }
    }


    fun step(client: Client) {
        try {
            client.ant?.idle()
        } catch (e: Exception) {
            console.log("Error with client ${client.id}: $e")
        }

    }


}



