package com.example.kino.connectoninfo

import android.content.Context
import android.net.*
import android.net.ConnectivityManager.NetworkCallback
import com.example.kino.connectoninfo.model.ConnectionType
import kotlinx.coroutines.flow.*
import timber.log.Timber

private val connectionListener: MutableStateFlow<ConnectionType> =
    MutableStateFlow(ConnectionType.Init())

fun connectionState(context: Context): StateFlow<ConnectionType> {
    val manager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val networkRequest = NetworkRequest
        .Builder()
        .build()
    manager.registerNetworkCallback(networkRequest, object : NetworkCallback() {
        override fun onAvailable(network: Network) {
            super.onAvailable(network)
            Timber.i("Connection")
            connectionListener.tryEmit(ConnectionType.Available(true))
        }

        override fun onLost(network: Network) {
            super.onLost(network)
            Timber.i("NoConnection")
            connectionListener.tryEmit(ConnectionType.Lost(true))
        }
    })
    return connectionListener.asStateFlow()
}