package me.manulorenzo.mapthecity.about

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import me.manulorenzo.mapthecity.data.Resource
import me.manulorenzo.mapthecity.data.source.Repository

class AboutViewModel(
    coroutinesIoDispatcher: CoroutineDispatcher = Dispatchers.IO,
    private val repository: Repository
) : ViewModel() {
    val aboutInfo: LiveData<Resource<AboutInfo>> =
        liveData(context = viewModelScope.coroutineContext + coroutinesIoDispatcher) {
            emit(Resource.Loading())
            val info = repository.getAbout()
            emit(info)
        }
}
