package com.example.cotucatdpd.graphics

import android.graphics.Canvas
import com.example.cotucatdpd.GameDisplay
import com.example.cotucatdpd.gameObject.Player
import com.example.cotucatdpd.gameObject.PlayerState

class Animator(playerSpriteArray: ArrayList<Sprite>) {
    private var playerSpriteArray = playerSpriteArray
    private var idNotMovingFrame = 0
    private var idMovingFrame = 1
    private var updatesBeforeNextFrame = 0
    private val MAX_UPDATES_BEFORE_NEXT_FRAME = 5

    fun draw(canvas: Canvas?, gameDisplay: GameDisplay, player: Player){
        when(player.getState().getState()){
            PlayerState.State.NOT_MOVING ->{
                drawFrame(canvas, gameDisplay, player, playerSpriteArray[idNotMovingFrame])
            }
            PlayerState.State.STARTED_MOVING ->{
                updatesBeforeNextFrame = 5
                drawFrame(canvas, gameDisplay, player, playerSpriteArray[idMovingFrame])
            }
            PlayerState.State.IS_MOVING ->{
                updatesBeforeNextFrame--
                if(updatesBeforeNextFrame == 0){
                    updatesBeforeNextFrame = MAX_UPDATES_BEFORE_NEXT_FRAME
                    toggleIdMovingFrame()
                }
                drawFrame(canvas, gameDisplay, player, playerSpriteArray[idMovingFrame])
            }
            else -> {return}
        }
    }
    fun toggleIdMovingFrame(){
        if(idMovingFrame == 1)
            idMovingFrame = 2
        else
            idMovingFrame = 1
    }

    fun drawFrame(canvas: Canvas?, gameDisplay: GameDisplay, player: Player, sprite: Sprite){
        sprite.draw(canvas,
            gameDisplay.gameToDisplayX(player.getPositionX()) - sprite.getWidth()/2,
            gameDisplay.gameToDisplayY(player.getPositionY()) - sprite.getHeight()/2)
    }
}