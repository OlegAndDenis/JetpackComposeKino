package com.example.kino.root

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.fragment.app.FragmentActivity
import com.example.ui_common_compose.theme.KinoTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RootActivity: FragmentActivity() {

    @ExperimentalFoundationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { 
            KinoTheme {
                Root()
            }
        }
    }
}