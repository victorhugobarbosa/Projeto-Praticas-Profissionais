package com.example.cotucatdpd.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import com.example.cotucatdpd.R

class Inicio : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inicio)

        var window = window
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        this.supportActionBar?.hide()
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