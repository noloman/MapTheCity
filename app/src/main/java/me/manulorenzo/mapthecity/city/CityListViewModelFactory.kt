package me.manulorenzo.mapthecity.city

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import me.manulorenzo.mapthecity.about.AboutViewModel
import me.manulorenzo.mapthecity.data.source.Repository

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