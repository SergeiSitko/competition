package by.footballcompetition.ui.standing

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import by.footballcompetition.R
import by.footballcompetition.api.ContentResource
import by.footballcompetition.api.ErrorResource
import by.footballcompetition.api.LoadingResource
import by.footballcompetition.api.NetworkException
import by.footballcompetition.data.Team
import by.footballcompetition.ui.team.TeamActivity
import kotlinx.android.synthetic.main.activity_standing.*
import org.koin.android.ext.android.getKoin
import org.koin.androidx.viewmodel.ext.android.viewModel

class StandingActivity : AppCompatActivity(R.layout.activity_standing) {

    private val viewModel by viewModel<StandingViewModel>()

    private val teamClick: (Team) -> Unit = {
        startActivity(TeamActivity.intent(this, it.id))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val code = intent.getStringExtra(COMPETITION_CODE) ?: throw IllegalStateException("code must be")
        getKoin().setProperty(COMPETITION_CODE, code)

        competition_name.text = intent.getStringExtra(COMPETITION_NAME) ?: throw java.lang.IllegalStateException("name must be")

        val adapter = StandingAdapter(teamClick)
        standing.adapter = adapter
        standing.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))

        viewModel.data.observe(this, { resource ->
            when (resource) {
                is ContentResource -> {
                    progress.visibility = View.GONE
                    standing.visibility = View.VISIBLE
                    adapter.addData(resource.content)
                    if (resource.content.isEmpty()) empty_state.visibility = View.VISIBLE
                }
                is LoadingResource -> {
                    progress.visibility = View.VISIBLE
                    standing.visibility = View.GONE
                    empty_state.visibility = View.GONE
                }
                is ErrorResource -> {
                    progress.visibility = View.GONE
                    standing.visibility = View.VISIBLE
                    Toast.makeText(this@StandingActivity, resource.error?.message, Toast.LENGTH_SHORT).show()
                    empty_state.visibility = View.GONE
                    if (resource.error is NetworkException) finish()
                }
            }
        })
    }

    companion object {
        val COMPETITION_CODE = "COMPETITION_CODE"
        private val COMPETITION_NAME = "COMPETITION_NAME"
        fun intent(context: Context, competitionId: String, competitionName: String) =
              Intent(context, StandingActivity::class.java).apply {
                  putExtra(COMPETITION_CODE, competitionId)
                  putExtra(COMPETITION_NAME, competitionName)
              }
    }
}