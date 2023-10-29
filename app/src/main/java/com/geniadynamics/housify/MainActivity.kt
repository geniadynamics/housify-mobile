package com.geniadynamics.housify

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import android.widget.Toast
import com.geniadynamics.housify.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val editText = findViewById<EditText>(R.id.editText)
        val submitButton = findViewById<Button>(R.id.submitButton)

        submitButton.setOnClickListener {
            val userInput = editText.text.toString()
            if (userInput.isNotEmpty()) {
                Toast.makeText(this, "Texto escrito: $userInput", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Escreva algo antes de enviar", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
