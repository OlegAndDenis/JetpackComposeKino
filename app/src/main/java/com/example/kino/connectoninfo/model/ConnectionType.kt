package com.example.kino.connectoninfo.model

sealed class ConnectionType {
    data class available(val isConnection: Boolean = false) : ConnectionType()
    data class lost(val isConnection: Boolean = false) : ConnectionType()
}