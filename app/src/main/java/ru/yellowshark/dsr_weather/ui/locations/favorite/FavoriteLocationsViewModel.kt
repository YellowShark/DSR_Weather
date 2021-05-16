package ru.yellowshark.dsr_weather.ui.locations.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.yellowshark.dsr_weather.domain.model.Location
import ru.yellowshark.dsr_weather.domain.repository.Repository
import ru.yellowshark.dsr_weather.ui.locations.base.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class FavoriteLocationsViewModel @Inject constructor(
    private val repository: Repository
) : BaseViewModel() {

    val favLocations: LiveData<List<Location>>
        get() = _favLocations
    private val _favLocations = MutableLiveData<List<Location>>()


    fun getFavLocations() {
        disposables.add(repository.getFavoriteLocations()
            .subscribe { list ->
                _favLocations.value = list
            })
    }

    fun updateIsFavorite(id: Int, fav: Boolean) {
        disposables.add(repository.updateIsFavorite(id, fav).subscribe())
    }
}