package com.example.cotucatdpd

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import androidx.core.content.ContextCompat

class Player(context: Context?, positionX: Double, positionY: Double, radius: Double) {

    private var positionX = positionX
    private var positionY = positionY
    private var radius = radius
    private var velocityX: Double? = 0.0
    private var velocityY: Double? = 0.0
    private val SPEED_PIXELS_PER_SECOND = 400.0
    private val MAX_SPEED = SPEED_PIXELS_PER_SECOND / GameLoop.MAX_UPS

    private var paint = Paint()

    private var color = ContextCompat.getColor(context!!, R.color.player)

    fun draw(canvas: Canvas){
        paint.setColor(color)
        canvas.drawCircle(positionX.toFloat(), positionY.toFloat(), radius.toFloat(), paint)
    }

    fun update(joystick: Joystick){
        velocityX = joystick.getActuatorX()!! *MAX_SPEED
        velocityY = joystick.getActuatorY()!!*MAX_SPEED
        positionX += velocityX!!
        positionY += velocityY!!
    }

    fun setPosition(X: Double, Y: Double) {
        positionX = X
        positionY = Y
    }
}