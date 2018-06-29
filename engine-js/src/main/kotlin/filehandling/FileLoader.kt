package filehandling

import ant.PlayerScript
import kotlinx.coroutines.experimental.await
import kotlin.js.Promise

private external fun require(path: String): Any?

private external interface FS {
    fun readdir(path: String): Promise<Array<String>>
    fun writeFile(path: String, text: String): Promise<Any>
}

private external interface PathModule {
    fun join(parent: String, child: String): String
}

object FileLoader {

    private var fsModule: FS? = require("mz/fs").unsafeCast<FS>()
    private var pathModule: PathModule? = require("path").unsafeCast<PathModule>()

    suspend fun walkDir(path: String): List<String> {
        val clients = mutableListOf<String>()
        println("walkdir $path with $fsModule and $pathModule")

        val files = fsModule?.readdir(path)?.await()

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

    suspend fun savelog(path: String, tick: Int, data: String) {
        val location = pathModule?.join(path, tick.toString())!!
        fsModule?.writeFile("${location}.json", data)?.await()

    }
}