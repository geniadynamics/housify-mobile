package org.geniadynamics.housify.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import org.geniadynamics.housify.data.model.InferenceRequest
import org.geniadynamics.housify.data.model.InferenceResponse
import org.geniadynamics.housify.data.network.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class InferenceRepository(private val apiService: ApiService) {

    fun postInferenceRequest(request: InferenceRequest): LiveData<InferenceResponse> {
        val data = MutableLiveData<InferenceResponse>()
        apiService.postInferenceRequest(request).enqueue(object : Callback<InferenceResponse> {
            override fun onResponse(call: Call<InferenceResponse>, response: Response<InferenceResponse>) {
                data.value = response.body()
            }

            override fun onFailure(call: Call<InferenceResponse>, t: Throwable) {
                // Handle failure
            }
        })
        return data
    }

    fun getUserRequests(email: String): LiveData<List<InferenceResponse>> {
        val data = MutableLiveData<List<InferenceResponse>>()
        apiService.getUserRequests(email).enqueue(object : Callback<List<InferenceResponse>> {
            override fun onResponse(call: Call<List<InferenceResponse>>, response: Response<List<InferenceResponse>>) {
                data.value = response.body()
            }

            override fun onFailure(call: Call<List<InferenceResponse>>, t: Throwable) {
                // Handle failure
            }
        })
        return data
    }
}
