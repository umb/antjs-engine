class Client(val id: String, val scriptPath: String, val ant: Ant)


//        js("""var path = "/Users/rick/Documents/dynmic-js-test/client.js";
//var module_holder = {};
//var res = {};
//var req = {};
//require(path)(module_holder);
//
//module_holder['user_getDetails'](req, res);
//        """)

external fun require(path: String): Any?


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

    fun loadClientES2015(): Ant? {
        val path = "/Users/rick/Documents/dynmic-js-test/client.js";
        val moduleHolder = require(path)


        return moduleHolder?.unsafeCast<Ant>()
    }


    fun loadClients() {

        val ant = loadClientES2015()
        ant?.move()


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
