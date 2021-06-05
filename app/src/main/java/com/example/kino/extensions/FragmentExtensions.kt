package com.example.kino.extensions

import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment

fun Fragment.findNavController(): NavController =
    NavHostFragment.findNavController(this)

fun Fragment.addOnBackPressedCallback(
    enabled: Boolean = true,
    onBackPressed: OnBackPressedCallback.() -> Unit
): OnBackPressedCallback {
    return object : OnBackPressedCallback(enabled) {
        override fun handleOnBackPressed() = onBackPressed()
    }.also { requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, it) }
}