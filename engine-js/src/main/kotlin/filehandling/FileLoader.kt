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

    private var fsModule: FS? = require("mz/fs").unsafeCast<FS>()
    private var pathModule: PathModule? = require("path").unsafeCast<PathModule>()

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

    fun savelog(path: String, tick: Int, data: String) {

        if (fsModule?.existsSync(path) == false) {
            fsModule?.mkdirSync(path)
        }
        val location = pathModule?.join(path, tick.toString())!!

        fsModule?.writeFileSync("${location}.json", data)

    }
}