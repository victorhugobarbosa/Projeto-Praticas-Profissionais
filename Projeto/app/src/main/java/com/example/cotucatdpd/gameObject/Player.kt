package com.example.cotucatdpd.gameObject

import android.content.Context
import android.graphics.Canvas
import androidx.core.content.ContextCompat
import com.example.cotucatdpd.GameDisplay
import com.example.cotucatdpd.GameLoop
import com.example.cotucatdpd.R
import com.example.cotucatdpd.gamePanel.*
import com.example.cotucatdpd.graphics.Sprite

    class Player(context: Context?, positionX: Double, positionY: Double, radius: Double, joystick: Joystick, animator: Animator)
    : Circle(ContextCompat.getColor(context!!, R.color.player), positionX, positionY, radius) {

    val SPEED_PIXELS_PER_SECOND = 400.0
    private val MAX_SPEED = SPEED_PIXELS_PER_SECOND / GameLoop.MAX_UPS
    val MAX_HEALTH_POINTS = 100
    private var joystick = joystick
    private var healthBar = HealthBar(this, context)
    private var healthPoints = MAX_HEALTH_POINTS
    private var animator = animator

    override fun update(){
        velocityX = joystick.getActuatorX()!! *MAX_SPEED
        velocityY = joystick.getActuatorY()!!*MAX_SPEED

        positionX += velocityX!!
        positionY += velocityY!!

        //Update direction
        if(velocityX != 0.0 || velocityY != 0.0)
        {
            var distance = getDistanceBetweenPoints(0.0, 0.0, velocityX!!, velocityY!!)
            directionX = velocityX!! / distance
            directionY = velocityY!! / distance
        }
    }

    fun getHealthPoints() : Int{
        return healthPoints
    }
    fun setHealthPoints(newHp: Int){
        if(newHp >= 0 )
            healthPoints = newHp
    }

    override fun draw(canvas: Canvas?, gameDisplay: GameDisplay){
        animator.draw(canvas, gameDisplay, this)
        healthBar.draw(canvas, gameDisplay)
    }
}