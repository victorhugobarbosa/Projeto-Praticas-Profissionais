package com.example.cotucatdpd

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class Inicio : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inicio)
    }

    fun loginPage(view: View){
        val intent = Intent(this, Login::class.java)
        startActivity(intent);
    }

    fun cadastroPage(view: View){
        val intent = Intent(this, Cadastro::class.java)
        startActivity(intent);
    }
}