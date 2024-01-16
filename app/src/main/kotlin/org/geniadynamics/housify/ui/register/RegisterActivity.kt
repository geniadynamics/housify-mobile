package org.geniadynamics.housify.ui.register

import android.os.Bundle
import android.view.View
import android.app.Activity
import android.content.Intent
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import org.geniadynamics.housify.R
import org.geniadynamics.housify.databinding.ActivityRegisterBinding
import org.geniadynamics.housify.ui.login.LoginActivity
import org.geniadynamics.housify.viewmodel.RegisterViewModel
import org.geniadynamics.housify.viewmodel.RegisterViewModelFactory


class RegisterActivity : AppCompatActivity() {

    private lateinit var registerViewModel: RegisterViewModel
    private lateinit var binding: ActivityRegisterBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var firstName = binding.firstName
        var lastName = binding.lastName
        var phone = binding.phoneNumber
        var email = binding.email
        var password = binding.password
        var birthDate = binding.birthDate
        var register = binding.createAccount
        var existingAccount = binding.alreadyHaveAccount


        registerViewModel =
            ViewModelProvider(this, RegisterViewModelFactory())[RegisterViewModel::class.java]

        registerViewModel.registFormState.observe(this@RegisterActivity, Observer {
            val registerState = it ?: return@Observer

            if (registerState.emailError != null) {
                email.error = getString(registerState.emailError)
            }
            if (registerState.passwordError != null) {
                password.error = getString(registerState.passwordError)
            }

        })

        registerViewModel.registResult.observe(this@RegisterActivity, Observer {
            val registerResult = it ?: return@Observer

            if (registerResult.error != null) {
                showRegisterFailed(registerResult.error)
            }
            if (registerResult.success != null) {
                Toast.makeText(this, "Register Successful", Toast.LENGTH_SHORT).show()
            }
            setResult(Activity.RESULT_OK)

            finish()
        })

        register.setOnClickListener{
            registerViewModel.register(
                firstName.toString(), lastName.toString(), email.toString(),
                password.toString(), birthDate.toString(), phone.toString())
        }

        existingAccount.setOnClickListener{
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }

    private fun showRegisterFailed(@StringRes errorString: Int) {
        Toast.makeText(applicationContext, errorString, Toast.LENGTH_SHORT).show()
    }
}