package com.ynavizovskyi.picturestestapp.presetntation.pages.newpictures

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import com.google.android.material.snackbar.Snackbar
import com.ynavizovskyi.picturestestapp.R
import com.ynavizovskyi.picturestestapp.domain.entity.Picture
import com.ynavizovskyi.picturestestapp.presetntation.base.BaseFragment
import com.ynavizovskyi.picturestestapp.presetntation.pages.PicturesAdapter
import kotlinx.android.synthetic.main.fragment_new_pictures.*
import javax.inject.Inject

class NewPicturesPageFragment : BaseFragment(R.layout.fragment_new_pictures) {


    @Inject
    lateinit var viewModel: NewPicturesViewModel

    private val pictureItemClickListener: (Picture) -> Unit = { picture ->
//        viewModel.markPictureAsSeen(picture, true)
        viewModel.startOrRestartMarkAsSeenCountDown(picture)
    }

    private val loadMoreListener: (Int) -> Unit = { nextPage ->
        viewModel.loadPage(nextPage)
    }

    private val contactsAdapter = PicturesAdapter(pictureItemClickListener, loadMoreListener)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        picturesRecyclerView.layoutManager = layoutManager
        picturesRecyclerView.adapter = contactsAdapter
        (picturesRecyclerView.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false


        observerData()
    }

    private fun observerData(){
        viewModel.newPicturesLiveData.observe(viewLifecycleOwner){ contacts ->
            contactsAdapter.data = contacts
        }

        viewModel.undoMarkAsSeenLiveData.observe(viewLifecycleOwner){ undo ->
            val mySnackbar = Snackbar.make(root, "DELETED", Snackbar.LENGTH_LONG)
            mySnackbar.setAction(R.string.undo){
                undo.undoAction.invoke()
            }
            mySnackbar.show()
        }
    }
}