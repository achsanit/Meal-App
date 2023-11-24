package com.achsanit.mealapp.utils

import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

fun View.makeVisible() {
    this.visibility = View.VISIBLE
}
fun View.makeGone() {
    this.visibility = View.GONE
}

fun View.disable() {
    isEnabled = false
    isClickable = false
    alpha = 0.5F
}

fun View.enable() {
    isEnabled = true
    isClickable = true
    alpha = 1F
}

fun <T> Fragment.collectLatestState(flow: Flow<T>, collect: suspend (T) -> Unit) {
    lifecycleScope.launch {
        repeatOnLifecycle(Lifecycle.State.STARTED) {
            flow.collectLatest(collect)
        }
    }
}
