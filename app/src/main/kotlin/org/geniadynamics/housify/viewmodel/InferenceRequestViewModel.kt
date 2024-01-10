package org.geniadynamics.housify.viewmodel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.geniadynamics.housify.data.model.Item

class InferenceRequestViewModel : ViewModel() {
    private val _items = MutableLiveData<List<Item>>()
    val items: LiveData<List<Item>> = _items

    init {
        // Load items (this would usually be fetched from a repository)
        _items.value = listOf(
            Item("Create a beautiful horizon", "https://wallup.net/wp-content/uploads/2019/09/5953-ocean-sunrise-jpg.jpg", "A peaceful, overcast beach scene with a calm ocean, moss-covered rocks, and hints of sunrise breaking through clouds", "Ocean sunrise"),
            Item("Create a beautiful horizon", "https://wallup.net/wp-content/uploads/2019/09/5953-ocean-sunrise-jpg.jpg", "A peaceful, overcast beach scene with a calm ocean, moss-covered rocks, and hints of sunrise breaking through clouds", "Ocean sunrise"),
            Item("Create a beautiful horizon", "https://wallup.net/wp-content/uploads/2019/09/5953-ocean-sunrise-jpg.jpg", "A peaceful, overcast beach scene with a calm ocean, moss-covered rocks, and hints of sunrise breaking through clouds", "Ocean sunrise"),
            Item("Create a beautiful horizon", "https://wallup.net/wp-content/uploads/2019/09/5953-ocean-sunrise-jpg.jpg", "A peaceful, overcast beach scene with a calm ocean, moss-covered rocks, and hints of sunrise breaking through clouds", "Ocean sunrise"),
        )
    }
}
