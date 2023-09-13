package com.example.cotucatdpd.graphics

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Rect
import com.example.cotucatdpd.R

class SpriteSheet(context: Context?) {
    private var bitmapOptions = BitmapFactory.Options()
    private var bitmap: Bitmap?

    init{
        bitmapOptions.inScaled = false
        bitmap = BitmapFactory.decodeResource(context!!.resources, R.drawable.personagem_pd, bitmapOptions)
    }

    fun getPlayerSprite() : Sprite{
        return Sprite(this, Rect(0, 0, 320, 320))
    }

    fun getBitmap(): Bitmap? {
        return bitmap
    }
}