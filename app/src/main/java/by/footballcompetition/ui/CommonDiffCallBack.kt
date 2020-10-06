package by.footballcompetition.ui

import androidx.recyclerview.widget.DiffUtil

class CommonDiffUtilsCallBack(
      val oldItems: List<Any>,
      val newItems: List<Any>
) : DiffUtil.Callback() {
    override fun getOldListSize() = oldItems.size
    override fun getNewListSize() = newItems.size
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
          oldItems.get(oldItemPosition) == newItems.get(newItemPosition)

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) = false
}