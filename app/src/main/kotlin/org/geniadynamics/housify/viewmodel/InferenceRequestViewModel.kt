package org.geniadynamics.housify.viewmodel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.geniadynamics.housify.data.model.InferenceRequest
import org.geniadynamics.housify.data.model.InferenceResponse
import org.geniadynamics.housify.data.model.Item
import org.geniadynamics.housify.data.repository.InferenceRepository

class InferenceViewModel(private val repository: InferenceRepository) : ViewModel() {
    private val responses = MutableLiveData<List<InferenceResponse>>(listOf())

    fun getUserRequests(email: String) {
        repository.getUserRequests(email).observeForever { newData ->
            responses.value = newData.reversed()
        }
    }

    fun postInferenceRequest(request: InferenceRequest): LiveData<InferenceResponse> {
        val postResponse = MutableLiveData<InferenceResponse>()
        repository.postInferenceRequest(request).observeForever { newResponse ->
            postResponse.value = newResponse
            val updatedList = responses.value!!.toMutableList().apply {
                add(0, newResponse)
            }
            responses.value = updatedList
        }
        return postResponse
    }

    fun getResponses(): LiveData<List<InferenceResponse>> {
        return responses
    }
}