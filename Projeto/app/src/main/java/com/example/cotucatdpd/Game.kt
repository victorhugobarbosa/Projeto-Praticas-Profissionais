package com.example.cotucatdpd

import android.content.Context
import android.graphics.Canvas
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView

class Game(context: Context?) : SurfaceView(context), SurfaceHolder.Callback {
    private val player: Player
    private var loop: GameLoop
    private val joystick: Joystick

    init {

        val surfaceHolder = holder
        surfaceHolder.addCallback(this)

        loop = GameLoop(this, surfaceHolder)

        joystick = Joystick(275, 300, 70, 40)
        player = Player(getContext(), 500.0, 500.0, 30.0)

        isFocusable = true
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {

        when(event?.action){
            MotionEvent.ACTION_DOWN -> {
                if(joystick.isPressed(event.x.toDouble(), event.y.toDouble())){
                    joystick.setIsPressed(true)
                }
                return true
            }
            MotionEvent.ACTION_MOVE -> {
                if(joystick.getIsPressed()){
                    joystick.setActuator(event.x.toDouble(), event.y.toDouble())
                }
                return true
            }
            MotionEvent.ACTION_UP -> {
                joystick.setIsPressed(false)
                joystick.resetActuator()
                return true
            }
        }
        return super.onTouchEvent(event)
    }

    override fun surfaceCreated(surfaceHolder: SurfaceHolder) {
        if (loop.getState().equals(Thread.State.TERMINATED)) {
            var surfHolder = holder
            surfHolder.addCallback(this)
            loop = GameLoop(this, surfHolder)
        }
        loop.startLoop()
    }

    override fun surfaceChanged(surfaceHolder: SurfaceHolder, i: Int, i1: Int, i2: Int) {}

    override fun surfaceDestroyed(surfaceHolder: SurfaceHolder) {}

    override fun draw(canvas: Canvas){
        super.draw(canvas)

        joystick.draw(canvas)
        player.draw(canvas)
    }
    fun update(){
        joystick.update()
        player.update(joystick)
    }
}