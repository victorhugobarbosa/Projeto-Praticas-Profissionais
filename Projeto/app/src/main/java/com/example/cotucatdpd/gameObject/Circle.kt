package com.example.cotucatdpd.gameObject

import android.graphics.Canvas
import android.graphics.Paint
import com.example.cotucatdpd.GameDisplay

abstract class Circle(color: Int, positionX: Double, positionY: Double,
                      radius: Double) : GameObject(positionX, positionY) {

    protected var radius = radius
    protected var paint = Paint()

    init{
        paint.setColor(color)
    }

    fun isColliding(obj1: Circle, obj2: Circle): Boolean {
        var distance = getDistanceBetweenObjects(obj1, obj2)
        var distanceToCollision = obj1.radius + obj2.radius
        if(distance < distanceToCollision) return true
        else return false
    }

    override fun draw(canvas: Canvas?, gameDisplay: GameDisplay){
        canvas!!.drawCircle(gameDisplay.gameToDisplayX(positionX),
                            gameDisplay.gameToDisplayY(positionY),
                            radius.toFloat(), paint)
    }

    fun getDistanceBetweenPoints(p1x: Double, p1y: Double, p2x: Double, p2y: Double): Double{
        return Math.sqrt(Math.pow(p1x - p2x, 2.0) + Math.pow(p1y - p2y, 2.0))
    }
}