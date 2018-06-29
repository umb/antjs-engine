import engine.Engine
import kotlinx.coroutines.experimental.launch

fun main() = launch {
    Engine.loadClients()
    Engine.simulate()


}