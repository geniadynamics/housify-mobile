package org.geniadynamics.housify.data.network

import org.geniadynamics.housify.data.model.InferenceRequest
import org.geniadynamics.housify.data.model.InferenceResponse

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query


interface ApiService {

    @POST("/inference-request")
    fun postInferenceRequest(@Body request: InferenceRequest): Call<InferenceResponse>

    @GET("/user-requests")
    fun getUserRequests(@Query("email") email: String): Call<List<InferenceResponse>>
}
