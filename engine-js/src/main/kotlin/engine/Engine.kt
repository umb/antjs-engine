package engine

import filehandling.FileLoader

class Client(val id: String, val scriptPath: String, val ant: Ant?)

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

    fun loadClientES2015(path: String): Ant? {
        val moduleHolder = require(path)
        return moduleHolder?.unsafeCast<Ant>()
    }


    fun loadClients() {
        val basepath = "/Users/rick/Documents/dynmic-js-test/clients"
        FileLoader.walkDir(basepath, then = { list -> loadAllClient(list) })

    }

    fun loadAllClient(clientDirectories: List<String>) {
        println(clientDirectories.size)
        for (dir in clientDirectories) {
            val clientId = dir.split("/").last()
            val scriptPath = "$dir/ant.js"

            println("loading client $clientId with script $scriptPath")

            val ant = loadClientES2015(scriptPath)
            val client = Client(clientId, scriptPath, ant)
            client.ant?.move()



            clients.add(client)
        }
    }


    fun step(client: Client) {
        try {
            client.ant?.move()
        } catch (e: Exception) {
            console.log("Error with client ${client.id}: $e")
        }

    }


}



