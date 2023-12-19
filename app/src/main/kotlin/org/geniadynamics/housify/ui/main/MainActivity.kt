package org.geniadynamics.housify.ui.main

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import org.geniadynamics.housify.R
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import android.widget.Toast
import org.geniadynamics.housify.ui.login.LoginActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_billinginfo)

/*        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)

        val editText = findViewById<EditText>(R.id.editText)
        val submitButton = findViewById<Button>(R.id.submitButton)

        submitButton.setOnClickListener {
            val userInput = editText.text.toString()
            if (userInput.isNotEmpty()) {
                Toast.makeText(this, "Texto escrito: $userInput", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Escreva algo antes de enviar", Toast.LENGTH_SHORT).show()
            }
        }*/
    }
}
