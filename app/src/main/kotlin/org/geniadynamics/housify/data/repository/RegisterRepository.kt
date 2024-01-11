package org.geniadynamics.housify.data.repository

import org.geniadynamics.housify.data.model.User
import org.geniadynamics.housify.data.source.remote.RegisterDataSource
import org.geniadynamics.housify.utility.Result

class RegisterRepository(val dataSource: RegisterDataSource) {

    var user: User? = null
        private set

    val isRegistered: Boolean
        get() = user != null

    init {
        user = null
    }

    fun register(firstName: String, lastName: String, email: String, password: String, birthDate: String, phone: String): Result<User> {
        val result = dataSource.register(firstName, lastName, email, password, birthDate, phone)

        if (result is Result.Success){
            setRegisteredUser(result.data)
        }

        return result
    }

    private fun setRegisteredUser(user: User){
        this.user = user
    }
}