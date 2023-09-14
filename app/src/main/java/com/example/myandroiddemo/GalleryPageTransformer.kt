package com.example.myandroiddemo

import android.view.View
import androidx.viewpager2.widget.ViewPager2

class GalleryPageTransformer(private val scaleFactor: Float, private val paddingInPx: Float) :
        ViewPager2.PageTransformer {

    override fun transformPage(page: View, position: Float) {
        val offset = Math.abs(position)

        val scale = 1 - (offset * scaleFactor)
        page.scaleX = scale
        page.scaleY = scale

        val translationX = (paddingInPx * (1 - scale) / 2f)
        page.translationX = if (position < 0) -translationX else translationX
    }
}