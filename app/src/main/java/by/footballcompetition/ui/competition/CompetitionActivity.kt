package by.footballcompetition.ui.competition

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import by.footballcompetition.R
import by.footballcompetition.api.ContentResource
import by.footballcompetition.api.ErrorResource
import by.footballcompetition.api.LoadingResource
import by.footballcompetition.data.Competition
import by.footballcompetition.ui.standing.StandingActivity
import kotlinx.android.synthetic.main.activity_competition.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class CompetitionActivity : AppCompatActivity(R.layout.activity_competition) {

    private val viewModel by viewModel<CompetitionsViewModel>()

    private val competitionClick: (Competition) -> Unit = {
        if (it.code == null) {
            Toast.makeText(this@CompetitionActivity, "Code is null", Toast.LENGTH_SHORT).show()
        } else {
            startActivity(StandingActivity.intent(this, it.code, it.name))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        val adapter = CompetitionAdapter(competitionClick)
        competitions.adapter = adapter
        competitions.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))

        viewModel.loadData()
        viewModel.data.observe(this, { resource ->
            when (resource) {
                is ContentResource -> {
                    competitions.visibility = View.VISIBLE
                    adapter.addData(resource.content)
                }
                is LoadingResource -> {
                    competitions.visibility = View.GONE
                }
                is ErrorResource -> {
                    competitions.visibility = View.VISIBLE
                    Toast.makeText(this@CompetitionActivity, resource.error?.message, Toast.LENGTH_SHORT).show()
                }
            }
        })
    }
}