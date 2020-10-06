package by.footballcompetition.ui.team

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.footballcompetition.api.*
import by.footballcompetition.data.Team
import kotlinx.coroutines.launch

class TeamsViewModel(
      val apiInterface: ApiInterface
) : ViewModel() {

    private val _data = MutableLiveData<Resource<Team>>()
    val data: LiveData<Resource<Team>> = _data

    fun loadTeam(teamId: Int) {
        viewModelScope.launch {
            _data.value = LoadingResource
            try {
                val response = apiInterface.getTeams()

                val teams = response.teams

                val findingTeam = teams.firstOrNull { it.id == teamId }

                if (findingTeam == null) {
                    _data.value = ErrorResource(Exception("team is not found"))
                } else {
                    _data.value = ContentResource(findingTeam)
                }
            } catch (e: Exception) {
                _data.value = ErrorResource(e)
            }
        }
    }
}