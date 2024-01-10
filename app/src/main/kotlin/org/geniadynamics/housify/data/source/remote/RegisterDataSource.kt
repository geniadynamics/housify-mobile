package org.geniadynamics.housify.data.source.remote


import org.geniadynamics.housify.data.model.User
import java.io.IOException
import org.geniadynamics.housify.utility.Result


class RegisterDataSource {

    fun register(firstName: String, lastName: String, email: String, password: String, birthDate: String, phone: String): Result<User> {
        return try {
            // TODO: handle register user
            val fakeUser = User("01-01-2000", "example@example.com","John", "password", "Doe", "123456789")
            Result.Success(fakeUser)
        } catch (e: Throwable) {
            Result.Error(IOException("Error registering", e))
        }
    }
}