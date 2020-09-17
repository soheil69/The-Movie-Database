package com.example.themoviedb.util

import android.text.Editable
import android.text.TextWatcher

class DateTextWatcher : TextWatcher {
    private var prevLength: Int = 0

    override fun beforeTextChanged(p0: CharSequence, p1: Int, p2: Int, p3: Int) {
        prevLength = p0.length
    }

    override fun onTextChanged(p0: CharSequence, p1: Int, p2: Int, p3: Int) {
    }

    override fun afterTextChanged(p0: Editable) {
        val length = p0.length
        if (prevLength < length && (length == 4 || length == 7)) p0.append("-")
    }
}