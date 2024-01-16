package org.geniadynamics.housify.viewmodel

import android.content.Intent
import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.geniadynamics.housify.data.repository.RegisterRepository
import org.geniadynamics.housify.ui.register.RegistFormState
import org.geniadynamics.housify.ui.register.RegistResult
import org.geniadynamics.housify.ui.register.RegistUserView
import org.geniadynamics.housify.utility.Result
import org.geniadynamics.housify.R
import org.geniadynamics.housify.ui.login.LoginActivity
import org.geniadynamics.housify.ui.login.LoginFormState
import org.geniadynamics.housify.ui.register.RegisterActivity

class RegisterViewModel(private val registerRepository:RegisterRepository) : ViewModel() {

    private val _registForm = MutableLiveData<RegistFormState>()
    val registFormState: LiveData<RegistFormState> = _registForm

    private val _registResult = MutableLiveData<RegistResult>()
    val registResult: LiveData<RegistResult> = _registResult

    fun register(firstName: String, lastName: String, email: String, password: String, birthDate: String, phone: String){
        val result = registerRepository.register(firstName, lastName, email, password, birthDate, phone)

        if (result is Result.Success){
            _registResult.value = RegistResult(success = RegistUserView(displayName = result.data.email))
        }
        else{
            _registResult.value = RegistResult(error = R.string.register_failed)
        }
    }

    fun registerDataChanged(email: String, password: String) {
        if (!isEmailValid(email)){
            _registForm.value = RegistFormState(emailError = R.string.email)
        } else if (!isPasswordValid(password)) {
            _registForm.value = RegistFormState(passwordError = R.string.invalid_password)
        } else {
            _registForm.value = RegistFormState(isDataValid = true)
        }
    }
    private fun isEmailValid(email: String): Boolean {
        return if (email.contains('@')) {
            Patterns.EMAIL_ADDRESS.matcher(email).matches()
        } else {
            email.isNotBlank()
        }
    }

    // A placeholder password validation check
    private fun isPasswordValid(password: String): Boolean {
        return password.length > 5
    }

}