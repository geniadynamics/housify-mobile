package org.geniadynamics.housify.ui.main

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import org.geniadynamics.housify.R
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import android.widget.Toast
import org.geniadynamics.housify.ui.home.HomeActivity
import org.geniadynamics.housify.ui.login.LoginActivity
import org.geniadynamics.housify.ui.welcome.WelcomeActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Assuming you have a function to check JWT token validity
        if (hasValidJwtToken()) {
            // If token is valid, go to home page
            startActivity(Intent(this, HomeActivity::class.java))
        } else {
            // If no valid token, go to welcome page
            startActivity(Intent(this, WelcomeActivity::class.java))
        }
        // Close MainActivity
        finish()
    }

    private fun hasValidJwtToken(): Boolean {
        // Implement your logic to check for JWT tokens
        // This is just a placeholder for your actual token validation logic
        return false
    }
}
