package me.manulorenzo.citymaps.about

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import me.manulorenzo.citymaps.city.data.source.Repository

@Suppress("UNCHECKED_CAST")
class AboutViewModelFactory(
    private val repository: Repository
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        AboutViewModel(repository) as T
}