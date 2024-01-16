package org.geniadynamics.housify.ui.main

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import android.os.Build
import androidx.core.content.ContextCompat

import org.geniadynamics.housify.ui.login.LoginActivity
import org.geniadynamics.housify.ui.home.HomeActivity
import org.geniadynamics.housify.ui.welcome.WelcomeActivity


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

         FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
             if (!task.isSuccessful) {
                 Log.w("MAIN", "Fetching FCM registration token failed", task.exception)
                 return@OnCompleteListener
             }

             // Get new FCM registration token
             val token = task.result

             Log.d("MAIN", token.toString())
         })
        askNotificationPermission()

        checkLoginStatusAndRedirect()
    }
    private fun askNotificationPermission() {
        val requestPermissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestPermission(),
        ) { isGranted: Boolean ->
            if (isGranted) {
                // FCM SDK (and your app) can post notifications.
            } else {
                askNotificationPermission()
            }
        }
        // This is only necessary for API level >= 33 (TIRAMISU)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) ==
                PackageManager.PERMISSION_GRANTED
            ) {
                // FCM SDK (and your app) can post notifications.
            } else if (shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS)) {
                // TODO: display an educational UI explaining to the user the features that will be enabled
                //       by them granting the POST_NOTIFICATION permission. This UI should provide the user
                //       "OK" and "No thanks" buttons. If the user selects "OK," directly request the permission.
                //       If the user selects "No thanks," allow the user to continue without notifications.
            } else {
                // Directly ask for the permission
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }
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
        if (sharedPreferences != null) {
            val email =  sharedPreferences.getString("isLoggedIn", null)
            if (email != null){
                return email.isNotEmpty()
            }
            return false
        }
        return false
    }
}