package com.example.cotucatdpd.gameObject

import android.content.Context
import android.graphics.Canvas
import androidx.core.content.ContextCompat
import com.example.cotucatdpd.GameDisplay
import com.example.cotucatdpd.GameLoop
import com.example.cotucatdpd.R
import com.example.cotucatdpd.graphics.Animator

class Enemy(context: Context?, player: Player, animator: Animator) :
    Circle(ContextCompat.getColor(context!!, R.color.enemy),
        Math.random()*1000, Math.random()*1000, 20.0) {

    private var player = player
    private val SPEED_PIXELS_PER_SECOND = player.SPEED_PIXELS_PER_SECOND*0.5
    private val MAX_SPEED = SPEED_PIXELS_PER_SECOND / GameLoop.MAX_UPS
    private var animator = animator
    private var inimigoState = InimigoState(this)

    private val SPAWNS_PER_MINUTE = 20
    private val SPAWNS_PER_SECOND = SPAWNS_PER_MINUTE/60.0
    private val UPDATES_PER_SPAWN = GameLoop.MAX_UPS /SPAWNS_PER_SECOND
    private var updatesTilNextSpawn = UPDATES_PER_SPAWN

    fun readyToSpawn(): Boolean {
        return if (updatesTilNextSpawn <= 0) {
            updatesTilNextSpawn += UPDATES_PER_SPAWN
            return true
        } else {
            updatesTilNextSpawn--
            return false
        }
    }

    override fun draw(canvas: Canvas?, gameDisplay: GameDisplay) {
        super.draw(canvas, gameDisplay)
        animator.draw(canvas, gameDisplay, player)
    }

    fun getInimigoState() : InimigoState {
        return inimigoState
    }

    override fun update() {
        var distanceToPlayerX = player.getPositionX() - positionX
        var distanceToPlayerY = player.getPositionY() - positionY

        var distanceToPlayer = getDistanceBetweenObjects(this, player)

        var directionX = distanceToPlayerX/distanceToPlayer
        var directionY = distanceToPlayerY/distanceToPlayer

        if(distanceToPlayer > 0){
            velocityX = directionX*MAX_SPEED
            velocityY = directionY*MAX_SPEED
        } else {
            velocityX = 0.0
            velocityY = 0.0
        }

        positionX += velocityX!!
        positionY += velocityY!!
    }
}
