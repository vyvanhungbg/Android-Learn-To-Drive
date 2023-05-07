package com.suspend.android.learntodrive.utils.extension

fun Int.toBrightness(): Int {
    return this * 255 / 100
}

fun Int.toPercentageString(): String {
    return "Zoom: $this%"
}
