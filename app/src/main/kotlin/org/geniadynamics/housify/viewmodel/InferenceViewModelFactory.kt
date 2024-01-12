package org.geniadynamics.housify.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.geniadynamics.housify.data.repository.InferenceRepository

class InferenceViewModelFactory(private val repository: InferenceRepository)
    : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(InferenceViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return InferenceViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
