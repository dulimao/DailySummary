package com.example.myandroiddemo

import android.util.Log
import android.view.View
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import java.lang.Math.abs

class VerticalStackPageTransformerWithRotation() : ViewPager2.PageTransformer {
    private val offscreenPageLimit: Int
    override fun transformPage(page: View, position: Float) {

        Log.i(TAG, "transformPage: view: " + page.getTag() + " position: " + position)
//        var alpha = 0.0f
//        if (position in 0.0f..1.0f) {
//            alpha = 1.0f - position
//        } else if (-1.0f <= position && position < 0.0f) {
//            alpha = position + 1.0f
//        }
//        view.setAlpha(alpha)


//        Log.i(TAG, "transformPage: position: " + position)
//        val pagerHeight = boundViewPager.height
//        val verticalOffsetBase = (pagerHeight - pagerHeight * CENTER_PAGE_SCALE) / 2 / offscreenPageLimit + DisplayUtil.dp2px(15f)
//        if (position >= offscreenPageLimit || position <= -1) {
//            view.visibility = View.GONE
//        } else {
//            view.visibility = View.VISIBLE
//        }
//        if (position >= 0) {
//            val translationX = -view.width * position
//            val translationY = -verticalOffsetBase * position
//            view.translationX = translationX
//            view.translationY = translationY
//        }
//        if (position > -1 && position < 0) {
//            val rotation = position * 30
//            view.rotation = rotation
//            view.alpha = position * position * position + 1
//        } else if (position > offscreenPageLimit - 1) {
//            view.alpha = (1 - position + Math.floor(position.toDouble())).toFloat()
//        } else {
//            view.rotation = 0f
//            view.alpha = 1f
//        }
//        if (position == 0f) {
//            view.scaleX = CENTER_PAGE_SCALE
//            view.scaleY = CENTER_PAGE_SCALE
//        } else {
//            val scaleFactor = Math.min(CENTER_PAGE_SCALE - position * 0.1f, CENTER_PAGE_SCALE)
//            view.scaleX = scaleFactor
//            view.scaleY = scaleFactor
//        }
//        ViewCompat.setElevation(view, (offscreenPageLimit - position) * 5)

        val scale = if(position.compareTo(-1f) <= 0 || position.compareTo(1f) >= 0) {
            SCALE
        } else if(position.compareTo(0.0f) == 0) {
            1f
        } else {
            SCALE + (1- abs(position)) * (1 - SCALE)
        }
        page.scaleX = scale
        page.scaleY = scale

        val pageOriginWidth = page.width
        val deltaLeft = (pageOriginWidth - pageOriginWidth * scale) / 2 + 1
        val space = 12
        when {
            position.compareTo(-2f) <= 0 -> {
                page.translationX = (3 * deltaLeft - 2 * space)
            }
            position.compareTo(-1f) < 0 -> {
                page.translationX = 3 * deltaLeft - 2 * space + (position + 2) * (-2 * deltaLeft + space)
            }
            position.compareTo(0f) < 0 -> {
                page.translationX = (deltaLeft - space) * Math.abs(position)
            }
            position.compareTo(1f) < 0 -> {
                page.translationX = (-deltaLeft + space) * position
            }
            position.compareTo(2f) < 0 -> {
                page.translationX = -deltaLeft + space + (position - 1) * (-2 * deltaLeft + space)
            }
            position.compareTo(2f) >= 0 -> {
                page.translationX = -3 * deltaLeft + 2 * space + (position - 2) * (-2 * deltaLeft + space)
            }
        }
    }

    companion object {
        private const val CENTER_PAGE_SCALE = 0.8f
        private const val SCALE = 0.8f
        private const val MIN_SCALE  = 0.75f
        private const val TAG = "VerticalStackPageTransf"
    }

    init {
        offscreenPageLimit = 6
    }
}