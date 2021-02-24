package com.example.kino.comonnscreen

import androidx.appcompat.app.AppCompatActivity
import com.example.kino.di.components.ActivityComponent

abstract class Base : AppCompatActivity() {
    abstract fun getActivityComponent() : ActivityComponent
}