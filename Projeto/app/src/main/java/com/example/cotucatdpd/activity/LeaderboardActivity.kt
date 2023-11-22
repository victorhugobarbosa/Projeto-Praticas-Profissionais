package com.example.cotucatdpd.activity

import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.cotucatdpd.R
import com.example.cotucatdpd.graphics.Sprite

class LeaderboardActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var window = window
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        setContentView(R.layout.activity_leaderboard)

        var txtRank = findViewById<TextView>(R.id.txtLeaderboard)

        val queue = Volley.newRequestQueue(this)
        val url = "http://192.168.11.101:3000/players"

        val jsonArrayRequest = JsonArrayRequest(
            Request.Method.GET, url, null,
            { response ->
                try {
                    val users = arrayOfNulls<String>(50)
                    val points = arrayOfNulls<Int>(50)

                    for (i in 0 until response.length()) {
                        if(i >= 49)
                            break;

                        val jsonObject = response.getJSONObject(i)
                        val name = jsonObject.getString("nickname")
                        val pontos = jsonObject.getInt("pontos")

                        users[i] = name
                        points[i] = pontos
                    }

                    txtRank.text = ordenarPorPontos(users, points)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            },
            { error ->
                Toast.makeText(applicationContext, "Error: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        )
        queue.add(jsonArrayRequest)

        findViewById<ImageView>(R.id.btnVoltar).setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    fun ordenarPorPontos(users: Array<String?>, points: Array<Int?>) : String{
        val ordenado = StringBuilder()
        for(i in 0 until users.size){
            if(points[i] == null)
                break
            for(j in 1 until users.size){
                if(points[j] == null)
                    break
                if(points[j]!! > points[i]!!){
                    var backup = points[i]
                    points[i] = points[j]
                    points[j] = backup

                    var backup2 = users[i]
                    users[i] = users[j]
                    users[j] = backup2
                }
            }
            ordenado.append("${users[i]}: ${points[i]}\n")
        }
        return ordenado.toString()
    }
}