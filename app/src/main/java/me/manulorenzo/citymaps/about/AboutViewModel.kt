package me.manulorenzo.citymaps.about

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import me.manulorenzo.citymaps.city.data.source.Repository
import me.manulorenzo.citymaps.data.Resource

class AboutViewModel(private val repository: Repository) : ViewModel() {
    val aboutInfo: LiveData<Resource<AboutInfo>> =
        liveData(context = viewModelScope.coroutineContext + Dispatchers.IO) {
            emit(Resource.Loading())
            val info = repository.getAbout()
            emit(info)
        }
}
