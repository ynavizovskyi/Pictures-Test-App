package com.ynavizovskyi.picturestestapp.presetntation.pages

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ynavizovskyi.picturestestapp.R
import com.ynavizovskyi.picturestestapp.domain.entity.Picture
import com.ynavizovskyi.picturestestapp.presetntation.PicturesViewModel
import com.ynavizovskyi.picturestestapp.presetntation.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_seen_pictures.*
import javax.inject.Inject

class SeenPicturesPageFragment : BaseFragment(R.layout.fragment_seen_pictures) {

    @Inject
    lateinit var viewModel: PicturesViewModel

    private val pictureItemClickListener: (Picture) -> Unit = { picture ->
        viewModel.markPictureAsSeen(picture, false)
    }

    private val contactsAdapter = PicturesAdapter(pictureItemClickListener)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        seenPicturesRecyclerView.layoutManager = layoutManager
        seenPicturesRecyclerView.adapter = contactsAdapter

        observerData()
    }

    private fun observerData(){
        viewModel.seenPicturesLiveData.observe(viewLifecycleOwner){ contacts ->
//            contactsAdapter.setData(contacts)
        }
    }
}