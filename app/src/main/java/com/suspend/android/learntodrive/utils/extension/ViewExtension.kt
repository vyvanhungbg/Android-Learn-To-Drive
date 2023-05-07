package com.suspend.android.learntodrive.utils.extension

import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.suspend.android.learntodrive.R


fun View.hide() {
    this.isVisible = false
}

fun View.show() {
    this.isVisible = true
}



/*fun View.enableDisableView(enabled: Boolean) {
    val view = this
    try {
        view.isEnabled = enabled
        if (view is ViewGroup) {
            val group = view
            for (idx in 0 until group.childCount) {
                view.enableDisableView(enabled)
            }
        }
    } catch (ex: Exception) {
        logError(message = ex.message)
    }
}*/

fun View.showSnackBar(message: String?, buttonText: String? = null, onClick: () -> Unit = {}) {
    val snackBar = Snackbar.make(this, message.toString(), Snackbar.LENGTH_LONG).apply {
        setAction(buttonText) {
            onClick()
            this.dismiss()
        }
        setTextColor(ContextCompat.getColor(this.context, R.color.color_main_900))
        setActionTextColor(ContextCompat.getColor(this.context, R.color.color_main_900))
        view.setBackgroundColor(ContextCompat.getColor(this.context, R.color.white))
    }
    snackBar.show()
}
