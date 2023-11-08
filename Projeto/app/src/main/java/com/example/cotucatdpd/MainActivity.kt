package com.example.cotucatdpd

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.example.cotucatdpd.databinding.ActivityMainBinding
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var window = window
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    fun inicioPage(view: View){
        val intent = Intent(this, Inicio::class.java)
        startActivity(intent);
    }

    fun leaderboardPage(view: View){
        val intent = Intent(this, LeaderboardActivity::class.java)
        startActivity(intent);
    }
}
