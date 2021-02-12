package com.ynavizovskyi.picturestestapp.presetntation.pages

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ynavizovskyi.picturestestapp.R
import com.ynavizovskyi.picturestestapp.domain.entity.Picture
import com.ynavizovskyi.picturestestapp.domain.entity.aspectRatio
import com.ynavizovskyi.picturestestapp.presetntation.ListItem
import com.ynavizovskyi.picturestestapp.presetntation.VIEW_TYPE_LOADING
import com.ynavizovskyi.picturestestapp.presetntation.VIEW_TYPE_PICTURE
import com.ynavizovskyi.picturestestapp.presetntation.loadImage
import kotlinx.android.synthetic.main.listitem_picture.view.*
import java.lang.IllegalArgumentException
import kotlin.properties.Delegates

class PicturesAdapter(
    private val itemClickListener: (Picture) -> Unit,
    private val loadMoreListener: (nextPage: Int) -> Unit
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var data: List<ListItem> by Delegates.observable(emptyList()) { _, old, new ->
        DiffUtil.calculateDiff(ListItemDiffCallback(old, new)).dispatchUpdatesTo(this)
    }

    override fun getItemViewType(position: Int) = data[position].viewType

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            VIEW_TYPE_PICTURE -> {
                val view = layoutInflater.inflate(R.layout.listitem_picture, parent, false)
                return PictureItemViewHolder(view, itemClickListener)
            }
            VIEW_TYPE_LOADING -> {
                val view = layoutInflater.inflate(R.layout.listitem_loading, parent, false)
                return LoadingViewHolder(view, loadMoreListener)
            }
            else -> {
                throw IllegalArgumentException("VIew type $viewType not supported")
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = data[position]
        when (item) {
            is ListItem.PictureItem -> (holder as? PictureItemViewHolder)?.bind(item)
            is ListItem.Loading -> (holder as? LoadingViewHolder)?.bind(item)
        }
    }

    override fun getItemCount() = data.size

}

class PictureItemViewHolder(
    itemView: View, private val itemClickListener: (Picture) -> Unit
) :
    RecyclerView.ViewHolder(itemView) {

    fun bind(item: ListItem.PictureItem) {
        val picture = item.picture

        itemView.idTextView.text = picture.id.toString()
        itemView.authorTextView.text = picture.author

        itemView.pictureImageView.post {
            itemView.pictureImageView.layoutParams.height =
                (itemView.pictureImageView.width.toFloat() / picture.aspectRatio()).toInt()
            itemView.pictureImageView.loadImage(picture.url, itemView.pictureImageView.width)
        }


        itemView.setOnClickListener { itemClickListener.invoke(picture) }
        itemView.countDownTextView.text = item.countDownValue?.toString()
        itemView.countDownTextView.isVisible = item.countDownValue != null

        val markerTextColorRes = if(picture.isSeen) R.color.green else R.color.red
        val markerTextRes = if(picture.isSeen) R.string.marker_seen else R.string.marker_new
        itemView.seenMarkerTextView.setTextColor(itemView.context.resources.getColor(markerTextColorRes))
        itemView.seenMarkerTextView.setText(markerTextRes)
    }

}

class LoadingViewHolder(
    itemView: View,
    private val loadMoreListener: (nextPage: Int) -> Unit
) :
    RecyclerView.ViewHolder(itemView) {

    fun bind(item: ListItem.Loading) {
        loadMoreListener.invoke(item.nextPage)
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
