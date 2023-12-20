package org.geniadynamics.housify.ui.main

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import org.geniadynamics.housify.ui.home.HomeActivity
import org.geniadynamics.housify.ui.welcome.WelcomeActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        checkLoginStatusAndRedirect()
    }

    override fun onResume() {
        super.onResume()
        checkLoginStatusAndRedirect()
    }

    private fun checkLoginStatusAndRedirect() {
        if (isUserLoggedIn()) {
            startActivity(Intent(this, HomeActivity::class.java))
        } else {
            startActivity(Intent(this, WelcomeActivity::class.java))
        }
        finish()
    }

    private fun isUserLoggedIn(): Boolean {
        val sharedPreferences = getSharedPreferences("AppPrefs", MODE_PRIVATE)
        return sharedPreferences.getBoolean("isLoggedIn", false)
    }
}
