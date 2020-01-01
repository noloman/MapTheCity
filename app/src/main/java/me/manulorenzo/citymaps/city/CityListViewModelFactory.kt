package me.manulorenzo.citymaps.city

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import me.manulorenzo.citymaps.about.AboutViewModel
import me.manulorenzo.citymaps.data.source.Repository

@Suppress("UNCHECKED_CAST")
class CityListViewModelFactory(
    private val repository: Repository
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(CityListViewModel::class.java) -> CityListViewModel(
                repository
            ) as T
            modelClass.isAssignableFrom(AboutViewModel::class.java) -> AboutViewModel(repository) as T
            else -> throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}