package org.geniadynamics.housify.ui.register

data class RegistFormState(
    val emailError: Int? = null,
    val passwordError: Int? = null,
    val isDataValid: Boolean = false
)