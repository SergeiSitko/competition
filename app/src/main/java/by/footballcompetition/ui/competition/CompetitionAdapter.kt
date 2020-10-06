package by.footballcompetition.ui.competition

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import by.footballcompetition.R
import by.footballcompetition.data.Competition
import by.footballcompetition.ui.CommonDiffUtilsCallBack
import kotlinx.android.synthetic.main.item_competition_view.view.*

class CompetitionAdapter(
      private val competitionClick: (Competition) -> Unit
) : RecyclerView.Adapter<CompetitionAdapter.CompetitionHolder>() {

    private val items = mutableListOf<Competition>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = CompetitionHolder(
          LayoutInflater.from(parent.context).inflate(R.layout.item_competition_view, parent, false)
    )

    override fun onBindViewHolder(holder: CompetitionHolder, position: Int) {
        holder.bind(items.get(position), competitionClick)
    }

    override fun getItemCount() = items.size

    fun addData(newItems: List<Competition>) {
        val diffCallback = CommonDiffUtilsCallBack(items, newItems)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        items.clear()
        items.addAll(newItems)
        diffResult.dispatchUpdatesTo(this)
    }

    class CompetitionHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(model: Competition, competitionClick: (Competition) -> Unit) {
            with(itemView) {
                country.text = model.name
                itemView.setOnClickListener { competitionClick.invoke(model) }
            }
        }
    }
}