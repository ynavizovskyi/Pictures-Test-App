package com.ynavizovskyi.picturestestapp.presetntation

import android.os.Bundle
import com.ynavizovskyi.picturestestapp.R
import com.ynavizovskyi.picturestestapp.presetntation.base.BaseActivity
import com.ynavizovskyi.picturestestapp.presetntation.pages.PicturesPagerAdapter
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))
        initPages()
    }

    private fun initPages(){
        val pages = listOf(NewPicturesPage(), SeenPicturesPage())
        viewPagerPictures.adapter =
            PicturesPagerAdapter(supportFragmentManager, this, pages)
        tabLayoutPictures.setupWithViewPager(viewPagerPictures)
    }

}