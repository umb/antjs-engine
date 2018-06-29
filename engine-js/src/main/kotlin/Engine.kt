class Client(val id: String, val scriptPath: String, val ant: Ant)


//        js("""var path = "/Users/rick/Documents/dynmic-js-test/client.js";
//var module_holder = {};
//var res = {};
//var req = {};
//require(path)(module_holder);
//
//module_holder['user_getDetails'](req, res);
//        """)


object Engine {
    private val clients = mutableListOf<Client>()

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

    fun loadClients() {


        val moduleHolder: dynamic = Any()
        js(
            """var path = "/Users/rick/Documents/dynmic-js-test/client.js";
var module_holder = {};
var res = {};
var req = {};
moduleHolder = require(path);
"""
        );

        println("loaded module $moduleHolder")

        val result = moduleHolder.move();
        console.log("read $result")


    }

    fun step() {
        for (client in clients) {
            try {
                client.ant.move()
            } catch (e: Exception) {

                console.log("Error with client ${client.id}: $e")
            }
        }
    }


}


fun main() {
    Engine.loadClients()

    Engine.step()
}
