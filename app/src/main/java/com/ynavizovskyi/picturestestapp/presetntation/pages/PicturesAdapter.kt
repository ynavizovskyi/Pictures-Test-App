package com.ynavizovskyi.picturestestapp.presetntation.pages

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ynavizovskyi.picturestestapp.R
import com.ynavizovskyi.picturestestapp.domain.entity.Picture
import com.ynavizovskyi.picturestestapp.presetntation.ListItem
import com.ynavizovskyi.picturestestapp.presetntation.loadImage
import kotlinx.android.synthetic.main.listitem_picture.view.*
import kotlin.properties.Delegates

class PicturesAdapter(
    private val itemClickListener: (Picture) -> Unit
) :
    RecyclerView.Adapter<PictureItemViewHolder>() {

    var data: List<ListItem> by Delegates.observable(emptyList()) { _, old, new ->
        DiffUtil.calculateDiff(ListItemDiffCallback(old, new)).dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PictureItemViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.listitem_picture, parent, false)
        return PictureItemViewHolder(view, itemClickListener)
    }

    override fun onBindViewHolder(holder: PictureItemViewHolder, position: Int) {
        val contact = data[position]
        holder.bind(contact)
    }

    override fun getItemCount() = data.size

}

class PictureItemViewHolder(
    itemView: View, private val itemClickListener: (Picture) -> Unit
) :
    RecyclerView.ViewHolder(itemView) {

    fun bind(item: ListItem) {
        (item as ListItem.PictureItem).let{
            itemView.idTextView.text = item.picture.id.toString()
            itemView.authorTextView.text = item.picture.author
            itemView.pictureImageView.loadImage(item.picture.url)
            itemView.setOnClickListener { itemClickListener.invoke(item.picture) }
            itemView.countDownTextView.text = item.countDownValue?.value?.toString() ?: ""
        }
    }

}

class ListItemDiffCallback(
    private val old: List<ListItem>,
    private val new: List<ListItem>
) : DiffUtil.Callback() {

    override fun getOldListSize() = old.size

    override fun getNewListSize() = new.size

    override fun areItemsTheSame(oldP: Int, newP: Int) = old[oldP].id == new[newP].id

    override fun areContentsTheSame(oldP: Int, newP: Int) = old[oldP] == new[newP]
}
