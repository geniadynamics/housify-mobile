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
        setContentView(R.layout.activity_welcome)
    }
}
