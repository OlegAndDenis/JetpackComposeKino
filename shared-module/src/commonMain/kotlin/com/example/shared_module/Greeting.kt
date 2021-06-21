package com.example.shared_module

class Greeting {
    fun greeting(): String {
        return "Hello, ${Platform().platform}!"
    }
}