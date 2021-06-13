package com.example.base.network.model

sealed class ConnectionType {
    data class Init(val isConnection: Boolean = false) : ConnectionType()
    data class Available(val isConnection: Boolean = false) : ConnectionType()
    data class Lost(val isConnection: Boolean = false) : ConnectionType()
}