package engine.math

import engine.Engine
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

data class Vec2(var x: Double, var y: Double) {
    companion object {
        fun randomScaled(scale: Int) = Vec2(Engine.random() * scale, Engine.random() * scale)

        /**
         * @param orientation angle in [0,2*PI]
         */
        fun ofAngle(orientation: Double): Vec2 {
            val x = cos(orientation)
            val y = sin(orientation)
            return Vec2(x, y).apply { normalize() }
        }
    }

    operator fun plus(other: Vec2): Vec2 {
        return Vec2(x + other.x, y + other.y)
    }

    operator fun minus(other: Vec2): Vec2 {
        return Vec2(x - other.x, y - other.y)
    }

    operator fun times(scalar: Double): Vec2 {
        return Vec2(x * scalar, y * scalar)
    }

    fun normalize() {
        val len = length
        x /= len
        y /= len
    }

    fun dist(other: Vec2) = (other - this).length

    fun dist2(other: Vec2) = (other - this).length2


    val normalized: Vec2
        get() = copy().apply { normalize() }

    val length: Double
        get() = sqrt(length2)


    val length2: Double
        get() = x * x + y * y
}
