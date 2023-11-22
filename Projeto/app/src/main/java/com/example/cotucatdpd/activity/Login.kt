package com.example.cotucatdpd.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.EditText
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.cotucatdpd.R

class Login : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        var window = window
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        this.supportActionBar?.hide()
    }

    fun login(view: View) {
        val nicknameText = findViewById<EditText>(R.id.txtNicknameLogin);
        val senhaText = findViewById<EditText>(R.id.txtSenhaLogin);

        val nickname = nicknameText.text.toString();
        val senha = senhaText.text.toString();

        if(nickname.isEmpty() || senha.isEmpty()){
            Toast.makeText(applicationContext,"Por favor, preencha todos os campos", Toast.LENGTH_SHORT).show();
            return
        }

        val queue = Volley.newRequestQueue(this)
        val url = "http://192.168.11.101:3000/players/$nickname"

        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                val correctPassword = response.optString("senha")
                if (senha == correctPassword) {
                    val goToGame = Intent(this, MainActivity::class.java)
                    goToGame.putExtra("nickname", nickname)
                    Toast.makeText(applicationContext,"Logado no email: ${response.optString("email")}", Toast.LENGTH_SHORT).show();
                    startActivity(goToGame)
                } else {
                    Toast.makeText(applicationContext,"Senha incorreta", Toast.LENGTH_SHORT).show();
                }
            },
            { error ->
                //
                // AlertDialog.Builder(applicationContext).setTitle("erro").setMessage(error.message).show()
                Toast.makeText(applicationContext,"Nickname incorreto", Toast.LENGTH_SHORT).show();
            }
        )
        queue.add(jsonObjectRequest)
    }

    fun goToRegister(view: View) {
        try {
            val goToRegister = Intent(this@Login, Cadastro::class.java)
            startActivity(goToRegister)
        } catch (e: Exception){
            Toast.makeText(applicationContext, "${e.message}", Toast.LENGTH_SHORT).show()
        }
    }
}