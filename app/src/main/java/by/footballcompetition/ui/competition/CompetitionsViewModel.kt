package by.footballcompetition.ui.competition

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.footballcompetition.api.*
import by.footballcompetition.data.Competition
import kotlinx.coroutines.launch

class CompetitionsViewModel(
      val apiInterface: ApiInterface
) : ViewModel() {

    private val _data = MutableLiveData<Resource<List<Competition>>>()
    val data: LiveData<Resource<List<Competition>>> = _data

    fun loadData() {
        viewModelScope.launch {
            _data.value = LoadingResource

            try {
                val response = apiInterface.getCompetitions()
                _data.value = ContentResource(response.competitions.filterNot { it.code == null })
            } catch (e: Exception) {
                _data.value = ErrorResource(e)
            }
        }
    }
}