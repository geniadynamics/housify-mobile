package org.geniadynamics.housify.ui.visimage

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import org.geniadynamics.housify.R

class VisImageActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_visimage)

        val userText = intent.getStringExtra("userText")
        val imageUrl = intent.getStringExtra("imageUrl")
        val additionalText = intent.getStringExtra("additionalText")
        val title = intent.getStringExtra("title")

        val mainImage = findViewById<ImageView>(R.id.mainImage)
        Glide.with(this)
            .load(imageUrl)
            .into(mainImage)

        val userTextView = findViewById<TextView>(R.id.userText)
        userTextView.text = userText

        val additionalTextView = findViewById<TextView>(R.id.additionalText)
        additionalTextView.text = additionalText

        val titleTextView = findViewById<TextView>(R.id.titleText)
        titleTextView.text = title
    }

}
