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

    fun getPlayerSpriteArray() : ArrayList<Sprite>{
        var spriteList = arrayListOf<Sprite>()
        spriteList[0] = Sprite(this, Rect(0*64, 0, 1*64, 64))
        spriteList[1] = Sprite(this, Rect(1*64, 0, 2*64, 64))
        spriteList[2] = Sprite(this, Rect(2*64, 0, 3*64, 64))

        return spriteList
    }

    fun getBitmap(): Bitmap? {
        return bitmap
    }
}