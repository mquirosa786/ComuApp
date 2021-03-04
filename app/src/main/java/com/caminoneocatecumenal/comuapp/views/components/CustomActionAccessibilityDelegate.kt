package com.caminoneocatecumenal.comuapp.views.components

import android.view.View
import android.view.accessibility.AccessibilityNodeInfo
import android.view.accessibility.AccessibilityNodeInfo.AccessibilityAction

class CustomActionAccessibilityDelegate(private val description: CharSequence) :
    View.AccessibilityDelegate() {
    override fun onInitializeAccessibilityNodeInfo(
        host: View,
        info: AccessibilityNodeInfo
    ) {
        super.onInitializeAccessibilityNodeInfo(host, info)
        val customClick = AccessibilityAction(
            AccessibilityAction.ACTION_CLICK.id, description
        )
        info.addAction(customClick)
    }

}