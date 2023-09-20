package com.example.cotucatdpd.graphics

import android.graphics.Canvas
import com.example.cotucatdpd.GameDisplay
import com.example.cotucatdpd.gameObject.Player

class Animator(playerSpriteArray: ArrayList<Sprite>) {
    private var playerSpriteArray = playerSpriteArray

    fun draw(canvas: Canvas?, gameDisplay: GameDisplay, player: Player){
        when(state){

        }
    }
    fun drawFrame(canvas: Canvas?, gameDisplay: GameDisplay, player: Player, sprite: Sprite){
        sprite.draw(canvas,
            gameDisplay.gameToDisplayX(player.getPositionX()) - sprite.getWidth()/2,
            gameDisplay.gameToDisplayY(player.getPositionY()) - sprite.getHeight()/2)
    }
}