package com.example.cotucatdpd

import android.app.Activity
import android.content.Context
import android.graphics.Canvas
import android.util.DisplayMetrics
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView
import com.example.cotucatdpd.gameObject.*
import com.example.cotucatdpd.gamePanel.*
import com.example.cotucatdpd.graphics.*

class Game(context: Context?) : SurfaceView(context), SurfaceHolder.Callback{

    private val player: Player
    private var loop: GameLoop
    private val joystick: Joystick
    private var enemyList = mutableListOf<Enemy>()
    private var spellList = mutableListOf<Spell>()
    private var enemy: Enemy? = null
    private var joystickPointerID = 0
    private var numberOfSpellsToCast = 0
    private var gameOver: GameOver?
    private var gameDisplay: GameDisplay?
    //private var spriteSheet = SpriteSheet(context)

    init {
        val surfaceHolder = holder
        surfaceHolder.addCallback(this)

        loop = GameLoop(this, surfaceHolder)

        gameOver = GameOver(context)

        val displayMetrics = DisplayMetrics()
        (context as Activity).windowManager.defaultDisplay.getMetrics(displayMetrics)

        val outerRadius = Math.sqrt((displayMetrics.widthPixels + displayMetrics.heightPixels).toDouble())+30
        val innerRadius = (Math.sqrt((displayMetrics.widthPixels + displayMetrics.heightPixels).toDouble())+30)/2
        joystick = Joystick(displayMetrics.widthPixels/8, displayMetrics.heightPixels/2, outerRadius.toInt(), innerRadius.toInt())

        val spriteSheet = SpriteSheet(context)
        val animator = Animator(spriteSheet.getPlayerSpriteArray())
        player = Player(getContext(), 500.0, 500.0, 30.0, joystick, animator)
        enemy = Enemy(getContext(), player)
        //enemy = Enemy(getContext(), player, 0.0, 0.0, 20.0)

        gameDisplay = GameDisplay(displayMetrics.widthPixels, displayMetrics.heightPixels, player)

        isFocusable = true
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {

        when(event?.actionMasked){
            MotionEvent.ACTION_DOWN -> {
                if(joystick.getIsPressed()){
                    numberOfSpellsToCast++
                    //spellList.add(Spell(context, player))
                }
                else if(joystick.isPressed(event.x.toDouble(), event.y.toDouble())){
                    joystickPointerID = event.getPointerId(event.actionIndex)
                    joystick.setIsPressed(true)
                } else {
                    numberOfSpellsToCast++
                    //spellList.add(Spell(context, player))
                }
                return true
            }
            MotionEvent.ACTION_POINTER_DOWN -> {
                if(joystick.getIsPressed()){
                    numberOfSpellsToCast++
                    //spellList.add(Spell(context, player))
                }
                else if(joystick.isPressed(event.x.toDouble(), event.y.toDouble())){
                    joystickPointerID = event.getPointerId(event.actionIndex)
                    joystick.setIsPressed(true)
                } else {
                    numberOfSpellsToCast++
                    //spellList.add(Spell(context, player))
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
                if(joystickPointerID == event.getPointerId(event.actionIndex)){
                    joystick.setIsPressed(false)
                    joystick.resetActuator()
                }
                return true
            }
        }
        return super.onTouchEvent(event)
    }

    override fun surfaceCreated(surfaceHolder: SurfaceHolder) {
        if (loop.getState().equals(Thread.State.TERMINATED)) {
            val surfHolder = holder
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
        player.draw(canvas, gameDisplay!!)
        for(e in enemyList){
            e.draw(canvas, gameDisplay!!)
        }
        for(s in spellList){
            s.draw(canvas, gameDisplay!!)
        }

        if(player.getHealthPoints() <= 0){
            gameOver!!.draw(canvas)
        }
        rotateCanvas(canvas)
    }

    fun update(){

        if(player.getHealthPoints() <= 0)
            return

        joystick.update()
        player.update()

        // Spawn enemy
        if (enemy!!.readyToSpawn()) {
            enemyList.add(enemy!!)
            enemy = Enemy(getContext(), player)
        }
        while(numberOfSpellsToCast > 0){
            spellList.add(Spell(context, player))
            numberOfSpellsToCast--
        }

        for (e in enemyList){
            e.update()
        }
        for(s in spellList){
            s.update()
        }

        var iteratorE = enemyList.iterator()
        var e = Enemy(context, player)
        while(iteratorE.hasNext()){
            var enemy = iteratorE.next()
            if(e.isColliding(enemy, player)){
                iteratorE.remove()
                player.setHealthPoints(player.getHealthPoints()-25)
                continue
            }
            var iteratorS = spellList.iterator()
            while(iteratorS.hasNext())
            {
                var spell = iteratorS.next()
                if(e.isColliding(spell, enemy)) {
                    iteratorE.remove()
                    iteratorS.remove()
                    break
                }
            }
        }
        gameDisplay!!.update()
    }

    fun rotateCanvas(canvas: Canvas?){
        var angleInRads = Math.atan2(joystick.getActuatorY()!!, joystick.getActuatorX()!!)
        var angleInDeg = angleInRads * 57

        canvas!!.rotate(angleInDeg.toFloat())
    }

    fun pause(){
        loop.stopLoop()
    }
}