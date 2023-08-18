package com.example.cotucatdpd

import android.content.Context
import android.os.Looper
import android.widget.ImageView
import com.example.cotucatdpd.databinding.FragmentThirdBinding

class Enemy(val img: Int, val health: Int, val speed: Int, val spawn: Int, val binding: FragmentThirdBinding, val context: Context?) {
    val newEnemy = ImageView(context)

    fun spawnEnemy(){
        if(spawn == 1){
            newEnemy.setBackgroundResource(img)
            newEnemy.scaleType = ImageView.ScaleType.FIT_XY
            newEnemy.translationY = 1500.toFloat()
        }
    }
    fun moveEnemy(){
        if(spawn == 1){
            newEnemy.translationY = newEnemy.translationY - speed

            newEnemy.invalidate()

            val handler = android.os.Handler(Looper.getMainLooper())
            val runnable = { moveEnemy() }

            handler.postDelayed(runnable, 1000)

            handler.removeCallbacks(runnable)
        }
    }
}