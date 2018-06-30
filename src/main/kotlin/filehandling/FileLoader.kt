package filehandling

import ant.PlayerScript

private external fun require(path: String): Any?

private external interface FS {
    fun readdirSync(path: String): Array<String>
    fun writeFileSync(path: String, text: String)
    fun mkdirSync(path: String)
    fun existsSync(path: String): Boolean
}

private external interface PathModule {
    fun join(parent: String, child: String): String
}

object FileLoader {

    private val fsModule: FS? = require("mz/fs").unsafeCast<FS>()
    private val pathModule: PathModule? = require("path").unsafeCast<PathModule>()
    private val requireFromString: dynamic = require("require-from-string")

    fun walkDir(path: String): List<String> {
        val clients = mutableListOf<String>()
        println("walkdir $path with $fsModule and $pathModule")

        val files = fsModule?.readdirSync(path)

        if (files != null) {
            println("found ${files.size} client directories")

            for (i in 0 until files.size) {
                val clientfolder = pathModule?.join(path, files[i])
                if (clientfolder != null) {
                    clients.add(clientfolder)
                }

            }
        } else {
            println("unexpected error while reading directoy $path")
        }

        return clients;
    }

    fun loadClientCode(path: String): PlayerScript? {
        val moduleHolder = require(path)

        return moduleHolder?.unsafeCast<PlayerScript>()
    }

    fun loadClientCodeDirect(code: String): PlayerScript? {
        val moduleHolder = requireFromString(code)
        return moduleHolder?.unsafeCast<PlayerScript>()
    }

    fun savelog(path: String, tick: Int, data: String) {

        if (fsModule?.existsSync(path) == false) {
            fsModule?.mkdirSync(path)
        }
        val location = pathModule?.join(path, tick.toString())!!

        fsModule?.writeFileSync("${location}.json", data)

    }

    fun putCode(path: String, playerId: String, code: String) {
        val location = pathModule?.join(path, playerId)!!

        if (fsModule?.existsSync(path) == false) {
            fsModule?.mkdirSync(path)

        }

        if (fsModule?.existsSync(location) == false) {
            fsModule?.mkdirSync(location)
        }

        fsModule?.writeFileSync("$location/ant.js", code)
    }
}