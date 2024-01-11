package org.geniadynamics.housify.ui.register

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import org.geniadynamics.housify.R
import org.geniadynamics.housify.databinding.ActivityRegisterBinding
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


        registerViewModel = ViewModelProvider(this, RegisterViewModelFactory())[RegisterViewModel::class.java]


    }
}