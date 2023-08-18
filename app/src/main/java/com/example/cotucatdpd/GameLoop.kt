package com.example.cotucatdpd

import android.graphics.Canvas
import android.view.SurfaceHolder

class GameLoop(thirdFragment: ThirdFragment, surfaceHolder: SurfaceHolder) : Thread() {

    private var MAX_UPS = 30
    private var UPS_PERIOD = 1E+3/MAX_UPS

    private var isRunning: Boolean = false
    private var surfaceHolder = surfaceHolder
    private var thirdFragment = thirdFragment

    fun startLoop(){
        isRunning = true
        start()
    }

    override fun run() {
        super.run()

        var updateCount = 0
        var frameCount = 0

        var startTime: Long
        var elapsedTime: Long
        var sleepTime: Long

        var canvas = Canvas()
        startTime = System.currentTimeMillis()
        while (isRunning){
            try{
                canvas = surfaceHolder.lockCanvas()
                synchronized(surfaceHolder){
                    thirdFragment.update()
                    updateCount++

                    thirdFragment.draw(canvas)
                }
            } catch(e: IllegalArgumentException){
                e.printStackTrace()
            } finally{
                if(canvas != Canvas()){
                    try{
                        surfaceHolder.unlockCanvasAndPost(canvas)
                        frameCount++
                    }catch (e: IllegalArgumentException){
                        e.printStackTrace()
                    }
                }
            }

            elapsedTime = System.currentTimeMillis() - startTime
            sleepTime = (updateCount*UPS_PERIOD - elapsedTime).toLong()
            if(sleepTime > 0){
                sleep(sleepTime)
            }

            while(sleepTime < 0){
                thirdFragment.update()
                updateCount++
                elapsedTime = System.currentTimeMillis() - startTime
                sleepTime = (updateCount*UPS_PERIOD - elapsedTime).toLong()
            }

            elapsedTime = System.currentTimeMillis() - startTime
            if(elapsedTime >= 1000 && updateCount < MAX_UPS-1){
                updateCount = 0
                frameCount = 0
                startTime = System.currentTimeMillis()
            }
        }
    }
}