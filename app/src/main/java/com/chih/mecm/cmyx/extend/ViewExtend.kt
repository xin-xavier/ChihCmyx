package com.chih.mecm.cmyx.extend

import android.view.View
import android.view.View.*

fun View.setVisible() {
    if (visibility != VISIBLE) {
        visibility = VISIBLE
    }
}

fun View.setInVisible() {
    if (visibility != INVISIBLE) {
        visibility = INVISIBLE
    }
}

fun View.setGone() {
    if (visibility != GONE) {
        visibility = GONE
    }
}