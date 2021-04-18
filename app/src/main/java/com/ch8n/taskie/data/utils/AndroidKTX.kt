package com.ch8n.taskie.data.utils

import android.graphics.Paint
import androidx.appcompat.widget.AppCompatTextView

fun AppCompatTextView.showStrikeThrough(show: Boolean) {
    paintFlags = if (show) {
        paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
    } else {
        paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
    }
}