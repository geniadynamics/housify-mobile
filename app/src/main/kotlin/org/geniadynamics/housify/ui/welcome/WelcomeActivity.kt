package org.geniadynamics.housify.ui.welcome

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import org.geniadynamics.housify.R
import org.geniadynamics.housify.ui.login.LoginActivity
import org.geniadynamics.housify.ui.register.RegisterActivity

class WelcomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        val btnCreateUser = findViewById<Button>(R.id.signUp)
        val btnLogin = findViewById<Button>(R.id.loginButton)

        btnCreateUser.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        btnLogin.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }
}