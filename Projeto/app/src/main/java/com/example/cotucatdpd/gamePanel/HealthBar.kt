package com.example.cotucatdpd.gamePanel

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import androidx.core.content.ContextCompat
import com.example.cotucatdpd.GameDisplay
import com.example.cotucatdpd.R
import com.example.cotucatdpd.gameObject.Player

class HealthBar(player: Player?, context: Context?) {
    private var player = player
    private var width = 100
    private var height = 20
    private var margin = 2;
    private var borderPaint = Paint()
    private var borderColor = ContextCompat.getColor(context!!, R.color.white)
    private var healthPaint = Paint()
    private var healthColor = ContextCompat.getColor(context!!, R.color.hp)

    init{
        borderPaint.setColor(borderColor)
        healthPaint.setColor(healthColor)
    }

    fun draw(canvas: Canvas?, gameDisplay: GameDisplay){
        var x = player!!.getPositionX()
        var y = player!!.getPositionY()
        var distanceToPlayer = 30
        var hpPercentage = (player!!.getHealthPoints().toFloat()/player!!.MAX_HEALTH_POINTS.toFloat())

        var borderLeft: Float?; var borderTop: Float?; var borderRight: Float?; var borderBottom: Float?
        borderLeft = (x - width/2).toFloat()
        borderRight = (x + width/2).toFloat()
        borderBottom = (y - distanceToPlayer).toFloat()
        borderTop = (borderBottom - height)
        canvas!!.drawRect(
            gameDisplay.gameToDisplayX(borderLeft.toDouble()),
            gameDisplay.gameToDisplayY(borderTop.toDouble()),
            gameDisplay.gameToDisplayX(borderRight.toDouble()),
            gameDisplay.gameToDisplayY(borderBottom.toDouble()), borderPaint)

        var healthLeft: Float?; var healthTop: Float?; var healthRight: Float?; var healthBottom: Float?; var healthWidth: Float?; var healthHeight: Float?
        healthWidth = (width - 2*margin).toFloat()
        healthHeight = (height - 2*margin).toFloat()
        healthLeft = borderLeft + margin
        healthRight = healthLeft + healthWidth*hpPercentage
        healthBottom = borderBottom - margin
        healthTop = healthBottom - healthHeight

        canvas.drawRect(
            gameDisplay.gameToDisplayX(healthLeft.toDouble()),
            gameDisplay.gameToDisplayY(healthTop.toDouble()),
            gameDisplay.gameToDisplayX(healthRight.toDouble()),
            gameDisplay.gameToDisplayY(healthBottom.toDouble()), healthPaint)
    }
}