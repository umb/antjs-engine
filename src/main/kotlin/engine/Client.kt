package engine

import ant.PlayerScript

class Client(val id: String, val scriptPath: String?, val playerScript: PlayerScript) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class.js != other::class.js) return false

        other as Client

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}
