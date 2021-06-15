package com.example.kino.root

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.fragment.app.FragmentActivity
import com.example.kino.Root
import com.example.ui_common_compose.theme.KinoTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RootActivity: FragmentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { 
            KinoTheme {
                Root()
            }
        }
    }
}