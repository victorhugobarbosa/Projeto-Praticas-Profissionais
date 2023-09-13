package com.example.cotucatdpd.gamePanel

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import androidx.core.content.ContextCompat
import com.example.cotucatdpd.R

class GameOver(context: Context?) {

    private var context = context

    fun draw(canvas: Canvas?){
        var text = "Tu é um fudido"

        var x = 800.0
        var y = 200.0

        var paint = Paint()
        var color = ContextCompat.getColor(context!!, R.color.gameOver)
        paint.setColor(color)
        var textSize = 150.0
        paint.textSize = textSize.toFloat()
        canvas!!.drawText(text, x.toFloat(), y.toFloat(), paint)
    }
}