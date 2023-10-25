package com.example.cotucatdpd

import com.example.cotucatdpd.gameObject.GameObject

class GameDisplay(widthPixels: Int, heightPixels: Int, center: GameObject) {
    private var displayOffsetX = 0.0
    private var displayOffsetY = 0.0
    private var displayCenterX = widthPixels/2.0
    private var displayCenterY = heightPixels/2.0
    private var gameCenterX = 0.0
    private var gameCenterY = 0.0
    private var centerObject: GameObject
    val DISPLAY_RECT: Rect = Rect(0, 0, widthPixels, heightPixels)

    init{
        update()
    }

    fun update(){
        gameCenterX = centerObject.getPositionX()
        gameCenterY = centerObject.getPositionY()

        displayOffsetX = displayCenterX - gameCenterX
        displayOffsetY = displayCenterY - gameCenterY
    }

    fun gameToDisplayX(x: Double): Float {
        return (x + displayOffsetX).toFloat()
    }

    fun gameToDisplayY(y: Double): Float {
        return (y + displayOffsetY).toFloat()
    }

    fun gameRect(): Rect {
        return Rect(
                (gameCenterX - widthPixels / 2).toInt(),
                (gameCenterY - heightPixels / 2).toInt(),
                (gameCenterX + widthPixels / 2).toInt(),
                (gameCenterY + heightPixels / 2).toInt()
        )
    }

}