package com.example.cotucatdpd.graphics

import android.graphics.Canvas
import com.example.cotucatdpd.GameDisplay
import com.example.cotucatdpd.gameObject.Enemy
import com.example.cotucatdpd.gameObject.InimigoState
import com.example.cotucatdpd.gameObject.Player
import com.example.cotucatdpd.gameObject.PlayerState

class Animator(spriteArray: Array<Sprite?>) {

    var spriteArray = spriteArray
    var updatesBeforeNextUpdateFrame = 0
    var idNotMoving = 0
    var idMoving = 1

    fun draw(canvas: Canvas?, gameDisplay: GameDisplay, player: Player){
        when(player.getPlayerState().getState()){
            PlayerState.State.NOT_MOVING -> {
                drawFrame(canvas, gameDisplay, player, spriteArray[idNotMoving])
            }
            PlayerState.State.STARTED_MOVING -> {
                updatesBeforeNextUpdateFrame = 5
                drawFrame(canvas, gameDisplay, player, spriteArray[idMoving])
            }
            PlayerState.State.IS_MOVING -> {
                updatesBeforeNextUpdateFrame--
                if(updatesBeforeNextUpdateFrame <= 0){
                    updatesBeforeNextUpdateFrame = 5
                    toggleIdMoving()
                }
                drawFrame(canvas, gameDisplay, player, spriteArray[idMoving])
            }
        }
    }
    fun draw(canvas: Canvas?, gameDisplay: GameDisplay, inimigos: Enemy){
        when(inimigos.getInimigoState().getState()){
            InimigoState.State.NOT_MOVING -> {
                drawFrame(canvas, gameDisplay, inimigos, spriteArray[idNotMoving])
            }
            InimigoState.State.STARTED_MOVING -> {
                updatesBeforeNextUpdateFrame = 5
                drawFrame(canvas, gameDisplay, inimigos, spriteArray[idMoving])
            }
            InimigoState.State.IS_MOVING -> {
                updatesBeforeNextUpdateFrame--
                if(updatesBeforeNextUpdateFrame <= 0){
                    updatesBeforeNextUpdateFrame = 5
                    toggleIdMoving()
                }
                drawFrame(canvas, gameDisplay, inimigos, spriteArray[idMoving])
            }
        }
    }
    fun toggleIdMoving(){
        if(idMoving == 1)
            idMoving = 2
        else
            idMoving = 1
    }

    fun drawFrame(canvas: Canvas?, gameDisplay: GameDisplay, player: Player, sprite: Sprite?){
        sprite!!.draw(canvas,
            gameDisplay.gameToDisplayX(player.getPositionX()) - sprite.getWidth()/8,
            gameDisplay.gameToDisplayY(player.getPositionY()) - sprite.getHeight()/8)
    }

    fun drawFrame(canvas: Canvas?, gameDisplay: GameDisplay, inimigos: Enemy, sprite: Sprite?){
        sprite!!.draw(canvas,
            gameDisplay.gameToDisplayX(inimigos.getPositionX()) - sprite.getWidth()/8,
            gameDisplay.gameToDisplayY(inimigos.getPositionY()) - sprite.getHeight()/8)
    }
}