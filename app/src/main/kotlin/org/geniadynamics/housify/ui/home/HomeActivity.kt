package org.geniadynamics.housify.ui.home


import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import org.geniadynamics.housify.R
import org.geniadynamics.housify.ui.camera.CameraActivity

class HomeActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnOpenCamera = findViewById<Button>(R.id.btnOpenCamera)
        btnOpenCamera.setOnClickListener {
            val intent = Intent(this, CameraActivity::class.java)
            startActivity(intent)
        }
    }
}