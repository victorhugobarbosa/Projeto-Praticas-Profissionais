package com.example.cotucatdpd

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
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

        Toast.makeText(applicationContext,"Seja bem-vindo!",Toast.LENGTH_SHORT).show()
    }

    fun register(view: View){
        val emailText = findViewById<EditText>(R.id.txtEmail);
        val nicknameText = findViewById<EditText>(R.id.txtNicknameCadastro);
        val senhaText= findViewById<EditText>(R.id.txtSenhaCadastro);
        val confirmarSenhaText = findViewById<EditText>(R.id.txtConfirmarSenha);

        val email = emailText.text.toString().trim();
        val nickname = nicknameText.text.toString().trim();
        val senha = senhaText.text.toString().trim();
        val confirmarSenha = confirmarSenhaText.text.toString().trim();

        if (email.isEmpty() || nickname.isEmpty() || senha.isEmpty() || confirmarSenha.isEmpty()) {
            Toast.makeText(applicationContext,"Por favor, preencha todos os campos",Toast.LENGTH_SHORT).show()
            return
        }
        if(confirmarSenha != senha){
            Toast.makeText(applicationContext,"Insira a senha correta",Toast.LENGTH_SHORT).show()
            return
        }

        val player = JSONObject();
        player.put("email", email);
        player.put("maiorRodada", 0);
        player.put("senha", senha);

        val queue = Volley.newRequestQueue(this)
        val url = "http://192.168.180.209:3000/test"

        Toast.makeText(applicationContext,"Cadastrando: ${player}",Toast.LENGTH_SHORT).show();

        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.POST, url, player,
            { reponse ->
                val loginPage = Intent(this, Login::class.java);
                startActivity(loginPage);
            },
            { error ->
                Toast.makeText(applicationContext,"Falha ao cadastrar: ${error.message}",Toast.LENGTH_SHORT).show()
            }
        )
        queue.add(jsonObjectRequest)
    }

}