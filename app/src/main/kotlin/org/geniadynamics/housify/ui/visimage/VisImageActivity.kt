package org.geniadynamics.housify.ui.visimage

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
import android.os.Environment


class VisImageActivity : AppCompatActivity() {

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                val imageUrl = intent.getStringExtra("imageUrl")
                downloadImage(imageUrl)
            } else {
                Log.e("org.geniadynamics.housify.ui.visimage.VisImageActivity", "Permission denied")
            }
        }

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
            onTransferButtonClick(imageUrl)
        }

        val shareButton = findViewById<ImageView>(R.id.shareButton)
        shareButton.setOnClickListener {
            onShareButtonClick(imageUrl)
        }
    }

    private fun onTransferButtonClick(imageUrl: String?) {
        Log.d("org.geniadynamics.housify.ui.visimage.VisImageActivity", "onTransferButtonClick - Button Clicked")
        if (imageUrl != null) {
            if (hasWriteExternalStoragePermission()) {
                Log.d("org.geniadynamics.housify.ui.visimage.VisImageActivity", "hasWriteExternalStoragePermission")
                downloadImage(imageUrl)
            } else {
                Log.d("org.geniadynamics.housify.ui.visimage.VisImageActivity", "Requesting write external storage permission")
                requestWriteExternalStoragePermission()
            }
        } else {
            Log.e("org.geniadynamics.housify.ui.visimage.VisImageActivity", "Image URL is null or empty")
        }
    }

    private fun hasWriteExternalStoragePermission(): Boolean {
        val permission = android.Manifest.permission.WRITE_EXTERNAL_STORAGE
        return ActivityCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestWriteExternalStoragePermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(
                this,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
        ) {
        } else {
            requestPermissionLauncher.launch(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }
    }

    private fun downloadImage(imageUrl: String?) {
        Log.d("org.geniadynamics.housify.ui.visimage.VisImageActivity", "downloadImage - Image URL: $imageUrl")
        if (imageUrl != null) {
            try {
                val url = URL(imageUrl)
                val connection = url.openConnection()
                connection.connect()
                val input = connection.getInputStream()

                val contentValues = ContentValues().apply {
                    put(MediaStore.Images.Media.DISPLAY_NAME, "image.png")
                    put(MediaStore.Images.Media.MIME_TYPE, "image/png")
                    put(MediaStore.MediaColumns.RELATIVE_PATH,Environment.DIRECTORY_PICTURES)
                }

                val resolver = contentResolver
                val imageUri =
                    resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)

                imageUri?.let { uri ->
                    resolver.openOutputStream(uri)?.use { outputStream ->
                        copyStream(input, outputStream)
                        Log.d(
                            "org.geniadynamics.housify.ui.visimage.VisImageActivity",
                            "Image downloaded successfully: $uri"
                        )
                    }

                    resolver.notifyChange(uri, null)
                }

                input.close()
            } catch (e: IOException) {
                e.printStackTrace()
                Log.e(
                    "org.geniadynamics.housify.ui.visimage.VisImageActivity",
                    "Image download failed: ${e.message}"
                )
            }
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

    @Throws(IOException::class)
    private fun copyStream(input: InputStream, output: OutputStream) {
        val buffer = ByteArray(1024)
        var bytesRead: Int
        while (input.read(buffer).also { bytesRead = it } != -1) {
            output.write(buffer, 0, bytesRead)
        }
    }
}