package com.example.graphqlapp.ui

import android.view.View
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter

@BindingAdapter("postBodyTrimmed")
fun setPostBodyTrimmed(textView: TextView, body: String) {
    val numberOfCharacters = 120
    if (body.length <= numberOfCharacters) {
        textView.text = body
    } else {
        val lastChar = body[numberOfCharacters]
        var lastIndex = 119
        if (lastChar != ' ') {
            val indexOfSpace = body.indexOf(" ", numberOfCharacters)
            if (indexOfSpace != -1) {
                lastIndex = indexOfSpace
                textView.text = body.substring(0, lastIndex) + "..."
            } else {
                textView.text = body.substring(0, lastIndex)
            }
        } else {
            textView.text = body.substring(0, lastIndex)
        }

    }
}

@BindingAdapter("isVisible")
fun setVisibility(
    view: View,
    isVisible: Boolean
) {
    view.isVisible = isVisible
}

