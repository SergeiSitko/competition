package by.footballcompetition.ui.standing

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.footballcompetition.api.*
import by.footballcompetition.data.Table
import kotlinx.coroutines.launch
import timber.log.Timber

class StandingViewModel(
      private val apiInterface: ApiInterface,
      private val completionCode: String
) : ViewModel() {

    private val _data = MutableLiveData<Resource<List<Table>>>()
    val data: LiveData<Resource<List<Table>>> = _data

    init {
        viewModelScope.launch {
            _data.value = LoadingResource

            try {
                val response = apiInterface.getStanding(completionCode)
                val content = response.standings.first().table.sortedByDescending { it.goalDifference }
                Timber.d("table is ${content.joinToString { "${it.team.name} - ${it.goalDifference}" }}")
                _data.value = ContentResource(content)
            } catch (e: Exception) {
                _data.value = ErrorResource(e.form())
            }
        }
    }
}