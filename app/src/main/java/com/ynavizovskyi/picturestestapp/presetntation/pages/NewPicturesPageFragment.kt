package com.ynavizovskyi.picturestestapp.presetntation.pages

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ynavizovskyi.picturestestapp.R
import com.ynavizovskyi.picturestestapp.domain.entity.Picture
import com.ynavizovskyi.picturestestapp.presetntation.PicturesViewModel
import com.ynavizovskyi.picturestestapp.presetntation.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_new_pictures.*
import javax.inject.Inject

class NewPicturesPageFragment : BaseFragment(R.layout.fragment_new_pictures) {

    @Inject
    lateinit var viewModel: PicturesViewModel

    private val pictureItemClickListener: (Picture) -> Unit = { picture ->
        viewModel.markPictureAsSeen(picture, true)
    }

    private val contactsAdapter = PicturesAdapter(pictureItemClickListener)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        picturesRecyclerView.layoutManager = layoutManager
        picturesRecyclerView.adapter = contactsAdapter

        observerData()
    }

    private fun observerData(){
        viewModel.newPicturesLiveData.observe(viewLifecycleOwner){ contacts ->
            contactsAdapter.setData(contacts)
        }
    }
}