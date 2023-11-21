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
    var idMoveUp = 1
    var idMoveDir = 4
    var idMoveDown = 7
    var idMoveLeft = 10

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
    fun draw(canvas: Canvas?, gameDisplay: GameDisplay, inimigos: Enemy, lookDirection: Int){
        when(inimigos.getInimigoState().getState()){
            InimigoState.State.NOT_MOVING -> {
                //1 - Cima, 2 - Direita, 3 - Baixo, 4 - Esquerda
                if(lookDirection == 1)
                    drawFrame(canvas, gameDisplay, inimigos, spriteArray[0])
                if(lookDirection == 2)
                    drawFrame(canvas, gameDisplay, inimigos, spriteArray[3])
                if(lookDirection == 3)
                    drawFrame(canvas, gameDisplay, inimigos, spriteArray[6])
                if(lookDirection == 4)
                    drawFrame(canvas, gameDisplay, inimigos, spriteArray[9])
            }
            InimigoState.State.STARTED_MOVING -> {
                updatesBeforeNextUpdateFrame = 5
                if(lookDirection == 1)
                    drawFrame(canvas, gameDisplay, inimigos, spriteArray[idMoveUp])
                if(lookDirection == 2)
                    drawFrame(canvas, gameDisplay, inimigos, spriteArray[idMoveDir])
                if(lookDirection == 3)
                    drawFrame(canvas, gameDisplay, inimigos, spriteArray[idMoveDown])
                if(lookDirection == 4)
                    drawFrame(canvas, gameDisplay, inimigos, spriteArray[idMoveLeft])
            }
            InimigoState.State.IS_MOVING -> {
                updatesBeforeNextUpdateFrame--
                if(updatesBeforeNextUpdateFrame <= 0){
                    updatesBeforeNextUpdateFrame = 5
                    toggleIdMoving()
                }
                if(lookDirection == 1)
                    drawFrame(canvas, gameDisplay, inimigos, spriteArray[idMoveUp])
                if(lookDirection == 2)
                    drawFrame(canvas, gameDisplay, inimigos, spriteArray[idMoveDir])
                if(lookDirection == 3)
                    drawFrame(canvas, gameDisplay, inimigos, spriteArray[idMoveDown])
                if(lookDirection == 4)
                    drawFrame(canvas, gameDisplay, inimigos, spriteArray[idMoveLeft])
            }
        }
    }
    fun toggleIdMoving(){
        if(idMoving == 1)
            idMoving = 2
        else
            idMoving = 1

        if(idMoveUp == 1)
            idMoveUp = 2
        else
            idMoveUp = 1

        if(idMoveDir == 4)
            idMoveDir = 5
        else
            idMoveDir = 4

        if(idMoveDown == 7)
            idMoveDown = 8
        else
            idMoveDown = 7

        if(idMoveLeft == 10)
            idMoveLeft = 11
        else
            idMoveLeft = 10
    }

    fun drawFrame(canvas: Canvas?, gameDisplay: GameDisplay, player: Player, sprite: Sprite?){
        sprite!!.draw(canvas,
            gameDisplay.gameToDisplayX(player.getPositionX()) - sprite.getWidth()/8,
            gameDisplay.gameToDisplayY(player.getPositionY()) - sprite.getHeight()/8 - 24)
    }

    fun drawFrame(canvas: Canvas?, gameDisplay: GameDisplay, inimigos: Enemy, sprite: Sprite?){
        sprite!!.draw(canvas,
            gameDisplay.gameToDisplayX(inimigos.getPositionX()) - sprite.getWidth()/8,
            gameDisplay.gameToDisplayY(inimigos.getPositionY()) - sprite.getHeight()/8 - 24)
    }
}