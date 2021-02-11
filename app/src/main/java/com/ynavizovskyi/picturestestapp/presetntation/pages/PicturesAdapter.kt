package com.ynavizovskyi.picturestestapp.presetntation.pages

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ynavizovskyi.picturestestapp.R
import com.ynavizovskyi.picturestestapp.domain.entity.Picture
import kotlinx.android.synthetic.main.listitem_picture.view.*

class PicturesAdapter(
    private val itemClickListener: (Picture) -> Unit
) :
    RecyclerView.Adapter<PicturesAdapter.ViewHolder>() {

    private var data: List<Picture> = emptyList()

    fun setData(contacts: List<Picture>) {
        data = contacts
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.listitem_picture, parent, false)
        return ViewHolder(view, itemClickListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val contact = data[position]
        holder.bind(contact)
    }

    override fun getItemCount() = data.size

    class ViewHolder(
        itemView: View, private val itemClickListener: (Picture) -> Unit
    ) :
        RecyclerView.ViewHolder(itemView) {

        fun bind(item: Picture) {
            itemView.idTextView.text = item.id.toString()
            itemView.authorTextView.text = item.author
            itemView.setOnClickListener { itemClickListener.invoke(item) }

        }

    }

}