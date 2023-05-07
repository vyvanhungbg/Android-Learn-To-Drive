package com.suspend.android.learntodrive.utils.extension

import android.widget.TextView
import androidx.core.text.HtmlCompat
import com.jakewharton.rxbinding4.widget.textChanges
import com.suspend.android.learntodrive.R

fun TextView.textChangesAfterTyping() = textChanges()
    .skipFirstAction()
    .ignoreFastAction()
    .withIOToMainThread()

fun TextView.setTextHTML(text: String?) {
    this.setText(HtmlCompat.fromHtml(text ?: "", HtmlCompat.FROM_HTML_MODE_LEGACY))
}

fun TextView.displayDescription(text: String?) {
    if (text.isNullOrEmpty().not()) {
        val header = this.context.getString(R.string.description)
        this.setTextHTML(header.plus(text))
    } else {
        this.text = ""
    }
}
