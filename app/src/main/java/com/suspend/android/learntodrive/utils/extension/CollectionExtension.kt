package com.suspend.android.learntodrive.utils.extension

import com.suspend.android.learntodrive.model.Answer


fun MutableList<Answer>.addNewList(newList: List<Answer>) {
    this.clear()
    this.addAll(newList)
}