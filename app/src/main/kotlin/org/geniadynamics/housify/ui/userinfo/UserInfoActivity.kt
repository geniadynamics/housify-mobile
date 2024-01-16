package org.geniadynamics.housify.ui.userinfo

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import org.geniadynamics.housify.R
import org.geniadynamics.housify.ui.welcome.WelcomeActivity

class UserInfoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_userinfo)

        val saveButton = findViewById<Button>(R.id.saveButton)

        saveButton.setOnClickListener {

            val intent = Intent(this, WelcomeActivity::class.java)

            startActivity(intent)
        }
    }
}
