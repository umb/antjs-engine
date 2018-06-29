import engine.Engine
import kotlinx.coroutines.experimental.launch

fun main() = launch {
    Engine.loadClients()

    for (i in 1..10) {
        Engine.simulate()
    }


}