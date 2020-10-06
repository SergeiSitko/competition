package by.footballcompetition.ui.team

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Html
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import by.footballcompetition.R
import by.footballcompetition.api.ContentResource
import by.footballcompetition.api.ErrorResource
import by.footballcompetition.api.LoadingResource
import coil.ImageLoader
import coil.decode.SvgDecoder
import coil.request.LoadRequest
import kotlinx.android.synthetic.main.acitivity_team.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class TeamActivity : AppCompatActivity(R.layout.acitivity_team) {

    private val viewModel by viewModel<TeamsViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val teamId = intent.extras?.getInt(TEAM_ID) ?: throw IllegalStateException("team id is absent")

        viewModel.loadTeam(teamId)

        viewModel.data.observe(this, { resource ->
            when (resource) {
                is ContentResource -> {
                    progress.visibility = View.GONE
                    content.visibility = View.VISIBLE

                    val team = resource.content
                    team_name.text = team.name
                    address.text = "address: ${team.address}"
                    founded.text = "founded: ${team.founded}"
                    website.text = "site: ${Html.fromHtml(team.website)}"
                    val teamPhone = if (team.phone.isBlank()) "Absent" else team.phone
                    phone.text = "phone: ${teamPhone}"
                    val teamEmail = if (team.email.isBlank()) "Absent" else team.email
                    email.text = "email: ${teamEmail}"

                    val imageLoader = ImageLoader.Builder(this)
                          .componentRegistry { add(SvgDecoder(this@TeamActivity)) }
                          .build()
                    val request = LoadRequest.Builder(this)
                          .data(team.crestUrl)
                          .error(R.drawable.ic_team)
                          .target(logo)
                          .build()
                    imageLoader.execute(request)
                }
                is LoadingResource -> {
                    content.visibility = View.GONE
                    progress.visibility = View.VISIBLE
                }
                is ErrorResource -> {
                    content.visibility = View.GONE
                    progress.visibility = View.GONE
                    Toast.makeText(this@TeamActivity, resource.error?.message, Toast.LENGTH_SHORT).show()
                    finish()
                }
            }
        })
    }

    companion object {
        private val TEAM_ID = "TEAM_ID"
        fun intent(context: Context, teamId: Int) =
              Intent(context, TeamActivity::class.java).apply {
                  putExtra(TEAM_ID, teamId)
              }
    }
}