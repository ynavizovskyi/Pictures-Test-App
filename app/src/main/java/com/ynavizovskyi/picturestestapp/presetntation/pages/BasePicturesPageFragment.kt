package com.ynavizovskyi.picturestestapp.presetntation.pages

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import com.google.android.material.snackbar.Snackbar
import com.ynavizovskyi.picturestestapp.R
import com.ynavizovskyi.picturestestapp.domain.entity.Picture
import com.ynavizovskyi.picturestestapp.presetntation.BasePicturesViewModel
import com.ynavizovskyi.picturestestapp.presetntation.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_pictures.*

abstract class BasePicturesPageFragment  : BaseFragment(R.layout.fragment_pictures) {

    protected abstract val viewModel: BasePicturesViewModel

    protected abstract val pictureItemClickListener: (Picture) -> Unit

    protected abstract val loadMoreListener: (Int) -> Unit

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
        viewModel.picturesLiveData.observe(viewLifecycleOwner){ contacts ->
            contactsAdapter.data = contacts
        }

        viewModel.undoMarkPictureLiveData.observe(viewLifecycleOwner){ undo ->
            val message = String.format(getString(R.string.undo_message), undo.picture.id, undo.itemClickCount)
            val mySnackbar = Snackbar.make(root, message, Snackbar.LENGTH_LONG)
            mySnackbar.setAction(R.string.undo){
                undo.undoAction.invoke()
            }
            mySnackbar.show()
        }
    }

}