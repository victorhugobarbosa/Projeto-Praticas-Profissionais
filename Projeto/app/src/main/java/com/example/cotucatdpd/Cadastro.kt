package com.example.cotucatdpd

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley

class Cadastro : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro)

        //val textView = findViewById<TextView>(R.id.txtNicknameCadastro)

        // Instantiate the RequestQueue.
        val queue = Volley.newRequestQueue(this)
        val url = "https://api-praticas3-default-rtdb.firebaseio.com/Player/ner0"

        // Request a string response from the provided URL.
        val stringRequest = StringRequest(
            Request.Method.GET, url,
            { response ->
                Toast.makeText(applicationContext, "Response is: ${response.substring(0, 500)}", Toast.LENGTH_SHORT).show()
                // Display the first 500 characters of the response string.

                //textView.text = "Response is: ${response.substring(0, 500)}"
            },
            {
                Toast.makeText(applicationContext, "That didn't work!", Toast.LENGTH_SHORT).show()
                //textView.text = "That didn't work!"
            })

        // Add the request to the RequestQueue.
        queue.add(stringRequest)
    }
}