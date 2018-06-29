package engine

import ant.Ant
import ant.PlayerAnt
import filehandling.FileLoader

class Client(val id: String, val scriptPath: String, val ant: PlayerAnt?)

external fun require(path: String): Any?


object Engine {
    private val clients: MutableList<Client> = mutableListOf()

    private fun loadSimple() {
        js(
            """var path = "/Users/rick/Documents/dynmic-js-test/client-simple.js";
var module_holder = {};
var res = {};
var req = {};
require(path)(module_holder);
module_holder['user_getDetails'](req, res);
        """
        )
    }

    fun loadClientES2015(path: String): PlayerAnt? {
        val moduleHolder = require(path)
        return moduleHolder?.unsafeCast<PlayerAnt>()
    }


    fun loadClients() {
        val basepath = "/Users/rick/Documents/dynmic-js-test/clients"
        FileLoader.walkDir(basepath, then = { list -> loadAllClient(list) })

    }

    fun loadAllClient(clientDirectories: List<String>) {
        for (dir in clientDirectories) {
            val clientId = dir.split("/").last()
            val scriptPath = "$dir/ant.js"

            println("loading client $clientId with script $scriptPath")

            val client = Client(clientId, scriptPath, loadClientES2015(scriptPath))

            //TODO this is hacky
            // client.ant.asDynamic()["moveStraight"] = 1

            val ant: Ant = Ant()

            client.ant.asDynamic().moveStraight = { dist: Int -> moveStraight(ant, dist) }


            step(client)

            clients.add(client)
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



