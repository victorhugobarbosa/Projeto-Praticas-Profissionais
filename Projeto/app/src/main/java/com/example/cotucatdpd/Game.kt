package com.example.cotucatdpd

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Canvas
import android.graphics.Paint
import android.util.DisplayMetrics
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.cotucatdpd.activity.MainActivity
import com.example.cotucatdpd.gameObject.*
import com.example.cotucatdpd.gamePanel.*
import com.example.cotucatdpd.graphics.*
import com.example.cotucatdpd.map.Tilemap
import org.json.JSONObject
import java.util.Timer
import kotlin.concurrent.timerTask

class Game(context: Context?, nome: String) : SurfaceView(context), SurfaceHolder.Callback{

    private var tilemap: Tilemap
    private val player: Player
    private var loop: GameLoop
    private val joystick: Joystick
    private var enemyList = mutableListOf<Enemy>()
    private var spellList = mutableListOf<Spell>()
    private var enemy: Enemy? = null
    private var joystickPointerID = 0
    private var numberOfSpellsToCast = 0
    private var gameOver: GameOver?
    private var gameDisplay: GameDisplay
    private var nome = nome
    private var animatorInimigo : Animator
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
        player = Player(getContext(), 4000.0, 4000.0, 30.0, joystick, animator)
        animatorInimigo = Animator(spriteSheet.getInimigosSpriteArray())
        enemy = Enemy(getContext(), player, animatorInimigo)
        //enemy = Enemy(getContext(), player, 0.0, 0.0, 20.0)

        gameDisplay = GameDisplay(displayMetrics.widthPixels, displayMetrics.heightPixels, player)

        // Iniciar Tilemap

        tilemap = Tilemap(spriteSheet);

        isFocusable = true

        if(nome != "no-name") {
            val queue = Volley.newRequestQueue(context)
            val url = "http://192.168.11.101:3000/players/$nome"

            val jsonObjectGet = JsonObjectRequest(
                Request.Method.GET, url, null,
                { response ->
                    player.updatePoints(response.getInt("pontos"))
                },
                { error ->
                    Toast.makeText(
                        context,
                        "Erro ao puxar pontos:" + error.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            )
            queue.add(jsonObjectGet)
        }
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

        // tilemap

        tilemap.draw(canvas, gameDisplay);

        joystick.draw(canvas)
        drawPoints(canvas)

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
        player.draw(canvas, gameDisplay!!)
    }

    var updating = false
    fun update(){

        if(player.getHealthPoints() <= 0){
            if(!updating){
                updating = true
                Timer().schedule(timerTask {
                    if(nome != "no-name"){
                        updatePoints(nome)
                    }
                    val intent = Intent(context, MainActivity::class.java)
                    context.startActivity(intent)
                }, 3000)
            }
            return
        }

        joystick.update()
        player.update()

        // Spawn enemy
        if (enemy!!.readyToSpawn()) {
            enemyList.add(enemy!!)
            enemy = Enemy(getContext(), player, animatorInimigo)
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
        var e = Enemy(context, player, animatorInimigo)
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
                    player.updatePoints(50)
                    break
                }
            }
        }

        gameDisplay!!.update()
    }

    fun drawPoints(canvas: Canvas){
        var text = "Pontos:"+player.getPoints()

        var x = 1100.0
        var y = 50.0

        var paint = Paint()
        var color = ContextCompat.getColor(context!!, R.color.white)
        paint.setColor(color)
        var textSize = 50.0
        paint.textSize = textSize.toFloat()
        canvas!!.drawText(text, x.toFloat(), y.toFloat(), paint)
    }

    fun updatePoints(nickname: String){
        val queue = Volley.newRequestQueue(context)
        val url = "http://192.168.11.101:3000/players/$nickname"

        var email = ""
        var senha = ""
        val jsonObjectGet = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                email = response.optString("email")
                senha = response.optString("senha")

                var jsonObject = JSONObject()
                jsonObject.put("email", email);
                jsonObject.put("pontos", player.getPoints())
                jsonObject.put("nickname", nickname)
                jsonObject.put("senha", senha);

                val jsonObjectRequest = JsonObjectRequest(
                    Request.Method.PUT, url, jsonObject,
                    { response ->
                        Toast.makeText(context, "Player atualizado", Toast.LENGTH_SHORT).show()
                    },
                    { error ->
                        Toast.makeText(context, "Erro:"+error.message, Toast.LENGTH_SHORT).show()
                    }
                )
                queue.add(jsonObjectRequest)
            },
            { error ->

            }
        )
        queue.add(jsonObjectGet)
    }

    fun rotateCanvas(canvas: Canvas?){
        var angleInRads = Math.atan2(joystick.getActuatorY()!!, joystick.getActuatorX()!!)
        var angleInDeg = angleInRads * 57

        canvas!!.rotate(
            angleInDeg.toFloat(), (display.width / 2).toFloat(),
            (display.height / 2).toFloat()
        )
    }

    fun pause(){
        loop.stopLoop()
    }
}