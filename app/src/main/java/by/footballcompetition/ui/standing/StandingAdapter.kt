package by.footballcompetition.ui.standing

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import by.footballcompetition.R
import by.footballcompetition.data.Table
import by.footballcompetition.data.Team
import by.footballcompetition.ui.CommonDiffUtilsCallBack
import coil.ImageLoader
import coil.decode.SvgDecoder
import coil.request.LoadRequest
import kotlinx.android.synthetic.main.item_standing_view.view.*

class StandingAdapter(val teamClick: (Team) -> Unit) : RecyclerView.Adapter<StandingAdapter.StandingHolder>() {

    private val items = mutableListOf<Table>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = StandingHolder(
          LayoutInflater.from(parent.context).inflate(R.layout.item_standing_view, parent, false)
    )

    override fun onBindViewHolder(holder: StandingHolder, position: Int) {
        holder.bind(items.get(position), position + 1, teamClick)
    }

    override fun getItemCount() = items.size

    fun addData(newItems: List<Table>) {
        val diffCallback = CommonDiffUtilsCallBack(items, newItems)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        items.clear()
        items.addAll(newItems)
        diffResult.dispatchUpdatesTo(this)
    }

    class StandingHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(model: Table, position: Int, teamClick: (Team) -> Unit) {
            with(itemView) {
                itemView.setOnClickListener { teamClick.invoke(model.team) }

                team_name.text = model.team.name
                team_diff.text = model.goalDifference.toString()
                place.text = position.toString()

                if (model.goalDifference >= 0) {
                    team_diff.setTextColor(ContextCompat.getColor(context, R.color.colorPrimary))
                } else {
                    team_diff.setTextColor(ContextCompat.getColor(context, android.R.color.holo_red_dark))
                }

                val imageLoader = ImageLoader.Builder(context)
                      .componentRegistry { add(SvgDecoder(context)) }
                      .build()
                val request = LoadRequest.Builder(this.context)
                      .data(model.team.crestUrl)
                      .error(R.drawable.ic_team)
                      .target(logo)
                      .build()
                imageLoader.execute(request)
            }
        }
    }
}

