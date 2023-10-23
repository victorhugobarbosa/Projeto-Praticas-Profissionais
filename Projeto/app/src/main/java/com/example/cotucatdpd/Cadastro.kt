package com.example.cotucatdpd

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject

class Cadastro : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro)
    }

    fun register(view: View){
        val emailView = findViewById<TextView>(R.id.txtEmail);
        val nicknameView = findViewById<TextView>(R.id.txtNicknameCadastro);
        val senhaView = findViewById<TextView>(R.id.txtSenhaCadastro);
        val confirmarSenhaView = findViewById<TextView>(R.id.txtConfirmarSenha);

        val email = emailView.text.toString().trim();
        val nickname = nicknameView.text.toString().trim();
        val senha = senhaView.text.toString().trim();
        val confirmarSenha = confirmarSenhaView.text.toString().trim();

        if (email.isEmpty() || nickname.isEmpty() || senha.isEmpty() || confirmarSenha.isEmpty()) {
            Toast.makeText(applicationContext,"Por favor, preencha todos os campos",Toast.LENGTH_SHORT)
            return
        }
        else if(confirmarSenha != senha){
            Toast.makeText(applicationContext,"Insira a senha correta",Toast.LENGTH_SHORT)
            return
        }

        val player = JSONObject();
        player.put("email", email);
        player.put("nickname", nickname);
        player.put("senha", senha)
        player.put("maiorRodada", 0);

        val queue = Volley.newRequestQueue(this)
        val url = "http://192.168.180.209:3000/test"

        val jsonRequest = JsonObjectRequest(
            Request.Method.POST, url, player,
            { reponse ->
                val loginPage = Intent(this, Login::class.java)
                startActivity(loginPage);
            },
            { error ->
                Toast.makeText(applicationContext,"Falha ao cadastrar: ${error.message}",Toast.LENGTH_SHORT)
            }
        )
        queue.add(jsonRequest)
    }

}