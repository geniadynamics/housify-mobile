package org.geniadynamics.housify.ui.visimage

import android.Manifest
import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.bumptech.glide.Glide
import org.geniadynamics.housify.R
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import java.net.URL
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Environment
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition


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

        val transferButton = findViewById<ImageView>(R.id.transferButton)
        transferButton.setOnClickListener {
            val imgUrl = intent.getStringExtra("imageUrl")
            imgUrl?.let { downloadImage(it) }
        }


        val shareButton = findViewById<ImageView>(R.id.shareButton)
        shareButton.setOnClickListener {
            onShareButtonClick(imageUrl)
        }
    }

    private fun downloadImage(imageUrl: String) {
        Glide.with(this)
            .asBitmap()
            .load(imageUrl)
            .into(object : CustomTarget<Bitmap>() {
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                        saveImageToGallery(resource)
                    }
                }

                override fun onLoadCleared(placeholder: Drawable?) {
                }
            })
    }


    @RequiresApi(Build.VERSION_CODES.Q)
    private fun saveImageToGallery(bitmap: Bitmap) {
        val contentValues = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, "Housify${System.currentTimeMillis()}.png")
            put(MediaStore.MediaColumns.MIME_TYPE, "image/png")
            put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DOWNLOADS)
        }

        val resolver = contentResolver
        val uri = resolver.insert(MediaStore.Downloads.EXTERNAL_CONTENT_URI, contentValues)

        try {
            uri?.let {
                val stream: OutputStream? = resolver.openOutputStream(it)
                stream?.use {
                    if (!bitmap.compress(Bitmap.CompressFormat.JPEG, 100, it))
                        throw IOException("Failed to save bitmap.")
                }
                Toast.makeText(this, "Image saved to gallery", Toast.LENGTH_LONG).show()
            } ?: throw IOException("Failed to create new MediaStore record.")
        } catch (e: IOException) {
            Toast.makeText(this, "Failed to save image: ${e.message}", Toast.LENGTH_LONG).show()
            e.printStackTrace()
        }
    }

    private fun onShareButtonClick(imageUrl: String?) {
        Log.d("org.geniadynamics.housify.ui.visimage.VisImageActivity", "onShareButtonClick - Button Clicked")
        if (imageUrl != null) {
            val shareIntent = Intent(Intent.ACTION_SEND)
            shareIntent.type = "text/plain"
            shareIntent.putExtra(Intent.EXTRA_TEXT, imageUrl)
            startActivity(Intent.createChooser(shareIntent, "Share Image Link"))
        }
    }

}