package com.example.cotucatdpd.gameObject

import android.graphics.Canvas
import com.example.cotucatdpd.GameDisplay

abstract class GameObject(positionX: Double, positionY: Double) {
    protected var positionX = positionX
    protected var positionY = positionY
    protected var velocityX: Double? = 0.0
    protected var velocityY: Double? = 0.0
    var directionX = 0.0
    var directionY = 0.0

    abstract fun draw(canvas: Canvas?, gameDisplay: GameDisplay)
    abstract fun update()
    @JvmName("getPositionX1")
    fun getPositionX(): Double {
        return positionX
    }
    @JvmName("getPositionY1")
    fun getPositionY(): Double{
        return positionY
    }
    protected fun getDistanceBetweenObjects(obj1: GameObject, obj2: GameObject) : Double{
        return Math.sqrt(Math.pow(obj2.getPositionX() - obj1.getPositionX(), 2.0) +
        Math.pow(obj2.getPositionY() - obj1.getPositionY(), 2.0))
    }
    @JvmName("getDirectionX1")
    fun getDirectionX(): Double{
        return directionX
    }
    @JvmName("getDirectionY1")
    fun getDirectionY(): Double{
        return directionY
    }
    @JvmName("getVelocityX1")
    fun getVelocityX(): Double{
        return velocityX!!
    }
    @JvmName("getVelocityY1")
    fun getVelocityY(): Double{
        return velocityY!!
    }
}