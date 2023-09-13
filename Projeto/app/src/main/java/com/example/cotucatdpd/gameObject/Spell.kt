package com.example.cotucatdpd.gameObject

import android.content.Context
import androidx.core.content.ContextCompat
import com.example.cotucatdpd.GameLoop
import com.example.cotucatdpd.R

class Spell(context: Context?, player: Player) :
    Circle(ContextCompat.getColor(context!!, R.color.spell), player.getPositionX(), player.getPositionY(), 5.0) {

    val SPEED_PIXELS_PER_SECOND = 400.0
    private val MAX_SPEED = SPEED_PIXELS_PER_SECOND / GameLoop.MAX_UPS
    private var spellCaster = player

    init{
        velocityX = spellCaster.getDirectionX()*MAX_SPEED
        velocityY = spellCaster.getDirectionY()*MAX_SPEED
    }

    override fun update() {
        positionX += velocityX!!
        positionY += velocityY!!
    }
}