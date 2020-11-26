package org.wordpress.android.ui.mysite

import androidx.recyclerview.widget.DiffUtil
import org.apache.commons.lang3.NotImplementedException
import org.wordpress.android.ui.mysite.MySiteItem.SiteInfoBlock

class MySiteAdapterDiffCallback(
    private val oldItems: List<MySiteItem>,
    private val updatedItems: List<MySiteItem>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldItems.size

    override fun getNewListSize(): Int = updatedItems.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldItems[oldItemPosition]
        val updatedItem = updatedItems[newItemPosition]
        return oldItem.type == updatedItem.type && when {
            oldItem is SiteInfoBlock && updatedItem is SiteInfoBlock -> true
            else -> throw NotImplementedException("Diff not implemented yet")
        }
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldItems[oldItemPosition] == updatedItems[newItemPosition]
    }
}
