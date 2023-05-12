package com.example.cryptotracker.utils

import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.view.updateLayoutParams
import androidx.datastore.preferences.preferencesDataStore
import com.google.android.material.internal.ViewUtils
import java.text.DecimalFormat


fun String?.orEmptyString(): String {
    return this ?: ""
}

fun Float.inMoneyFormat(): String {
    return DecimalFormat("#,###.##").format(this)
}

val Context.dataStore by preferencesDataStore(name = "min_max_prefs")

fun View.hideKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(windowToken, 0)
}

@SuppressLint("RestrictedApi")
fun View.enableFloatingWithKeyboard(initialBottomMargin: Int) {
    ViewUtils.doOnApplyWindowInsets(
        this
    ) { v, insets, initialPadding ->
        initialPadding.run {
            val newBottomMargin = initialBottomMargin + insets.systemWindowInsetBottom
            v.updateLayoutParams<ViewGroup.MarginLayoutParams> {
                setMargins(start, top, end, newBottomMargin)
            }
        }
        insets
    }
}
