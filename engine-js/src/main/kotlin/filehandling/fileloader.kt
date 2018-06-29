package filehandling

import kotlinx.coroutines.experimental.await
import kotlin.js.Promise

/*
function LoadModules(path) {
    fs.lstat(path, function(err, stat) {
        if (stat.isDirectory()) {
            // we have a directory: do a tree walk
            fs.readdir(path, function(err, files) {
                var f, l = files.length;
                for (var i = 0; i < l; i++) {
                f = path_module.join(path, files[i]);
                LoadModules(f);
            }
            });
        } else {
            // we have a file: load it
            require(path)(module_holder);
        }
    });
}
var DIR = path_module.join(__dirname, 'lib', 'api');
LoadModules(DIR);
        */

external fun require(path: String): Any?


external interface FS {
    fun readdir(path: String): Promise<Array<String>>
}

external interface Stats {
    fun isDirectory(): Boolean
}

external interface PathModule {
    fun join(parent: String, child: String): String
}

object FileLoader {

    private var fsModule: FS?
    private var pathModule: PathModule?

    init {
        fsModule = require("mz/fs").unsafeCast<FS>()
        pathModule = require("path").unsafeCast<PathModule>()
    }

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

//   fun walkDir(path: String, then: (List<String>) -> Unit) {
//       val clients = mutableListOf<String>()
//       println("walkdir $path with $fsModule and $pathModule")
//
//       fsModule?.readdir(path) { err, files ->
//
//           if (err != null) {
//               println("Error while reading dir $path")
//           } else if (files != null) {
//               println("found ${files.size} client directories")
//
//               for (i in 0 until files.size) {
//                   val clientfolder = pathModule?.join(path, files[i])
//                   if (clientfolder != null) {
//                       clients.add(clientfolder)
//                   }
//
//               }
//           } else {
//               println("unexpected error while reading directoy $path")
//           }
//
//           then(clients)
//       }
//   }
//
}