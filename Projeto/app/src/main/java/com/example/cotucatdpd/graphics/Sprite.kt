package com.example.cotucatdpd.graphics

import android.graphics.Canvas
import android.graphics.Rect

class Sprite(spriteSheet: SpriteSheet, rect: Rect) {

    private val spriteSheet = spriteSheet
    private val rect = rect

    fun draw(canvas: Canvas?, x: Float, y: Float) {
        canvas!!.drawBitmap(spriteSheet.getBitmap()!!, rect, Rect(x.toInt()-24, y.toInt(), (x+40).toInt(), (y+64).toInt()), null)
    }

    fun getHeight() : Int{
        return rect.height()
    }
    fun getWidth() : Int{
        return rect.width()
    }
}