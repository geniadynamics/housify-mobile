package org.geniadynamics.housify.data.model

data class User(
    val birth_date: String,
    val email: String,
    val first_name: String,
    val hashed_password: String,
    val last_name: String,
    val phone: String
)