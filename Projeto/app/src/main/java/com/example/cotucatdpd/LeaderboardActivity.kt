package com.example.cotucatdpd
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import com.android.volley.toolbox.JsonObjectRequest

class LeaderboardActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_leaderboard)

        val url = "http://177.220.18.34:3000/players"

        // Chamar o método para obter dados do Leaderboard
        getLeaderboardData(url)
    }

    // Método para obter dados do Leaderboard
    private fun getLeaderboardData(url: String) {
        val queue = Volley.newRequestQueue(this)

        val jsonArrayRequest = JsonArrayRequest(
            Request.Method.GET, url, null,
            { response ->
                val players = mutableListOf<String>()

                // Iterar sobre os jogadores no Leaderboard
                for (i in 0 until response.length()) {
                    val playerObject = response.getJSONObject(i)
                    val playerName = playerObject.optString("nickname")
                    val playerPoints = playerObject.optInt("pontos")

                    // Construir a string com o nome e os pontos
                    val playerInfo = "$playerName: Pontos - $playerPoints"

                    players.add(playerInfo)
                }

                // Ordenar a lista de jogadores em ordem decrescente de pontos
                val sortedPlayers = players.sortedByDescending {
                    it.substringAfterLast("Pontos - ").toIntOrNull() ?: 0
                }

                // Concatenar os nomes dos jogadores separados por quebras de linha
                val playersText = sortedPlayers.joinToString("\n")

                // Encontrar o TextView no layout e atualizar seu conteúdo
                val textViewLeaderboard = findViewById<TextView>(R.id.textViewLeaderboard)
                textViewLeaderboard.text = playersText
            },
            { error ->
                // Lidar com erro ao obter dados do Leaderboard
                Toast.makeText(applicationContext, "Erro ao obter dados do Leaderboard", Toast.LENGTH_SHORT).show()
            }
        )

        queue.add(jsonArrayRequest)
    }
}
