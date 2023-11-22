package com.example.cotucatdpd.activity

import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.cotucatdpd.R

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

        findViewById<ImageView>(R.id.btnVoltar).setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        val queue = Volley.newRequestQueue(this)
        val url = "http://192.168.180.71:3000/players"

        val jsonArrayRequest = JsonArrayRequest(
            Request.Method.GET, url, null,
            { response ->
                try {
                    val users = StringBuilder()

                    for (i in 0 until response.length()) {
                        val jsonObject = response.getJSONObject(i)
                        val name = jsonObject.getString("nome")

                        users.append("$name\n")
                    }

                    txtRank.text = users.toString()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            },
            { error ->
                Toast.makeText(applicationContext, "Error to pull name: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        )
        queue.add(jsonArrayRequest)
    }
}