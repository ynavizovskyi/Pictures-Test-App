package com.ynavizovskyi.picturestestapp.presetntation.pages.seenpictures

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.ynavizovskyi.picturestestapp.R
import com.ynavizovskyi.picturestestapp.domain.entity.Picture
import com.ynavizovskyi.picturestestapp.presetntation.base.BaseFragment
import com.ynavizovskyi.picturestestapp.presetntation.pages.PicturesAdapter
import kotlinx.android.synthetic.main.fragment_new_pictures.*
import kotlinx.android.synthetic.main.fragment_seen_pictures.*
import javax.inject.Inject

class SeenPicturesPageFragment : BaseFragment(R.layout.fragment_seen_pictures) {

    @Inject
    lateinit var viewModel: SeenPicturesViewModel

    private val pictureItemClickListener: (Picture) -> Unit = { picture ->
        viewModel.startOrRestartMarkAsNewCountDown(picture)
    }

    private val contactsAdapter = PicturesAdapter(pictureItemClickListener, {})

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        seenPicturesRecyclerView.layoutManager = layoutManager
        seenPicturesRecyclerView.adapter = contactsAdapter

        observerData()
    }

    private fun observerData(){
        viewModel.seenPicturesLiveData.observe(viewLifecycleOwner){ contacts ->
            contactsAdapter.data = contacts
        }

        viewModel.undoMarkAsSeenLiveData.observe(viewLifecycleOwner){ undo ->
            val mySnackbar = Snackbar.make(root, "RETURNED", Snackbar.LENGTH_LONG)
            mySnackbar.setAction(R.string.undo){
                undo.undoAction.invoke()
            }
            mySnackbar.show()
        }
    }
}