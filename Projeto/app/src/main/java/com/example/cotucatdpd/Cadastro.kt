package com.example.cotucatdpd

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject


class Cadastro : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro)

        var window = window
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        this.supportActionBar?.hide()
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
            Toast.makeText(applicationContext,"Preencha todos os campos",Toast.LENGTH_SHORT).show()
            return
        }
        if(confirmarSenha != senha){
            Toast.makeText(applicationContext,"Erro ao confirmar senha",Toast.LENGTH_SHORT).show()
            return
        }

        val player = JSONObject();
        player.put("email", email);
        player.put("maiorRodada", 1);
        player.put("nickname", nickname)
        player.put("senha", senha);

        val queue = Volley.newRequestQueue(this)
        val url = "http://192.168.180.71:3000/players"
        var exists = false

        val getRequest = JsonArrayRequest(
            Request.Method.GET, url, null,
            { response ->
                for (i in 0 until response.length()) {
                    val jsonObject = response.getJSONObject(i)
                    val existingNickname = jsonObject.getString("nickname")

                    if (nickname == existingNickname) {
                        exists = true
                        break
                    }
                }
                if(!exists){
                    val jsonObjectRequest = JsonObjectRequest(
                        Request.Method.POST, url, player,
                        { reponse ->
                            Toast.makeText(applicationContext,"Conta cadastrada!",Toast.LENGTH_SHORT).show();
                            val loginPage = Intent(this, Login::class.java);
                            Toast.makeText(applicationContext,"Seja bem-vindo!",Toast.LENGTH_SHORT).show()
                            startActivity(loginPage);
                        },
                        { error ->
                            Toast.makeText(applicationContext,"Falha ao cadastrar: ${error.message}",Toast.LENGTH_SHORT).show()
                        }
                    )
                    queue.add(jsonObjectRequest)
                }else{
                    Toast.makeText(applicationContext,"Nickname já  está sendo utilizado!", Toast.LENGTH_SHORT).show();
                }
            }
        ) { error -> Toast.makeText(applicationContext,"Erro: ${error}", Toast.LENGTH_SHORT).show(); }
        queue.add(getRequest)
    }

}