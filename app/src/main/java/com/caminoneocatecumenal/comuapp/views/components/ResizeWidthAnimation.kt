package com.caminoneocatecumenal.comuapp.views.components

import android.view.View
import android.view.animation.Animation
import android.view.animation.Transformation

class ResizeWidthAnimation(private val view: View, private val width: Int) :
    Animation() {
    private val startWidth: Int
    override fun applyTransformation(
        interpolatedTime: Float,
        t: Transformation
    ) {
        view.layoutParams.width = startWidth + ((width - startWidth) * interpolatedTime).toInt()
        view.requestLayout()
    }

    override fun willChangeBounds(): Boolean {
        return true
    }

    init {
        startWidth = view.measuredWidth
    }
}