package com.example.cotucatdpd.gamePanel

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import com.example.cotucatdpd.gameObject.Circle

class Joystick(centerPositionX: Int, centerPositionY: Int, outerCircleRadius: Int, innerCircleRadius: Int) :
    Circle(0, 0.0, 0.0, 0.0){

    private var outerCircleCenterX = centerPositionX
    private var outerCircleCenterY = centerPositionY
    private var innerCircleCenterX = centerPositionX
    private var innerCircleCenterY = centerPositionY

    private var outerCircleRadius = outerCircleRadius
    private var innerCircleRadius = innerCircleRadius
    private var outerCirclePaint: Paint?
    private var innerCirclePaint: Paint?
    private var touchDistance: Double? = null
    private var isPressed = false
    private var actuatorX: Double? = 0.0
    private var actuatorY: Double? = 0.0

    init{
        outerCirclePaint = Paint()
        outerCirclePaint!!.setColor(Color.GRAY)
        outerCirclePaint!!.style = Paint.Style.FILL_AND_STROKE

        innerCirclePaint = Paint()
        innerCirclePaint!!.setColor(Color.BLUE)
        innerCirclePaint!!.style = Paint.Style.FILL_AND_STROKE
    }

    fun draw(canvas: Canvas?) {
        canvas?.drawCircle(outerCircleCenterX.toFloat(), outerCircleCenterY.toFloat(), outerCircleRadius.toFloat(), outerCirclePaint!!)
        canvas?.drawCircle(innerCircleCenterX.toFloat(), innerCircleCenterY.toFloat(), innerCircleRadius.toFloat(), innerCirclePaint!!)
    }

    override fun update() {
        updateInnerCircle()
    }

    private fun updateInnerCircle() {
        innerCircleCenterX = (outerCircleCenterX + actuatorX!! * outerCircleRadius.toDouble()).toInt()
        innerCircleCenterY = (outerCircleCenterY + actuatorY!! * outerCircleRadius.toDouble()).toInt()
    }

    fun isPressed(touchX: Double, touchY: Double): Boolean {
        touchDistance = Math.sqrt(Math.pow(outerCircleCenterX - touchX, 2.0) +
                                  Math.pow(outerCircleCenterY - touchY, 2.0)
        )
        return touchDistance!! < outerCircleRadius
    }

    fun setIsPressed(boolean: Boolean) {
        isPressed = boolean
    }

    fun getIsPressed(): Boolean {
        return isPressed
    }

    fun setActuator(x: Double, y: Double) {
        var deltaX = x - outerCircleCenterX
        var deltaY = y - outerCircleCenterY
        var deltaDistance = Math.sqrt(Math.pow(deltaX, 2.0) + Math.pow(deltaY, 2.0))

        if(deltaDistance < outerCircleRadius){
            actuatorX = deltaX/outerCircleRadius
            actuatorY = deltaY/outerCircleRadius
        } else {
            actuatorX = deltaX/deltaDistance
            actuatorY = deltaY/deltaDistance
        }

    }

    fun resetActuator() {
        actuatorX = 0.0
        actuatorY = 0.0
    }

    fun getActuatorX(): Double? {
        return actuatorX

    }

    fun getActuatorY(): Double? {
        return actuatorY
    }

}
