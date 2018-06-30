import engine.Engine
import server.ExpressApp
import server.Game

external fun require(module: String): dynamic

fun main() {
    // Engine.loadClients()

    //  for (i in 1..10) {
    //      Engine.simulate()
    //  }

    var engine: Engine? = null

    println(Engine.guid())

    val express = require("express")
    val app: ExpressApp = express();

    app.asDynamic().use(express.json())

    app.get("/") { req, res ->
        res.send(engine?.game)
    }

    app.post("/game") { req, res ->
        val currentEngine = engine
        if (currentEngine == null || currentEngine.game.tick >= currentEngine.game.maxDuration) {
            currentEngine?.cleanup()

            val size = (req.query.size as? String)?.toInt() ?: 100
            val duration = (req.query.maxDuration as? String)?.toInt() ?: 10_000

            val id = Engine.guid()

            val game = Game(id, size, duration)
            engine = Engine(game)
        } else {
            res.status(409)
        }

        res.send(engine?.game)
    }

    app.put("/player/:id") { req, res ->

        if (engine == null) {
            res.status(409)
        } else {
            val playerId: String = req.params.id

            val code = req.body.code
            engine?.addPlayer(playerId, code)
        }

        res.send("")
    }

    app.post("/step") { req, res ->

        val frames = Frames(engine?.game)

        if (engine == null) {
            res.status(409)
        } else {
            val steps: Int = (req.query.steps as? String)?.toInt() ?: 1

            for (i in 1..steps) {
                val gamestate = engine?.simulate()
                frames.frames.add(JSON.stringify(gamestate))
            }
        }

        res.send(frames)
    }


    app.listen(3000) {
        println("Listening on port http://localhost:3000")
    }

}

data class Frames(val game: Game?, val frames: MutableList<String> = mutableListOf())