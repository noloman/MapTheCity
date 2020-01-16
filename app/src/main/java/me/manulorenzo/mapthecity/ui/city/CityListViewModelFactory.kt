package me.manulorenzo.mapthecity.ui.city

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import me.manulorenzo.mapthecity.data.source.Repository
import me.manulorenzo.mapthecity.ui.about.AboutViewModel

@Suppress("UNCHECKED_CAST")
class CityListViewModelFactory(
    private val repository: Repository
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(CityListViewModel::class.java) -> CityListViewModel(
                repository = repository
            ) as T
            modelClass.isAssignableFrom(AboutViewModel::class.java) -> AboutViewModel(repository = repository) as T
            else -> throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}