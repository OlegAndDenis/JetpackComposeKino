package com.example.kino.connectoninfo

import android.net.*
import android.net.ConnectivityManager.NetworkCallback
import android.os.Build
import com.example.kino.connectoninfo.model.ConnectionType
import kotlinx.coroutines.flow.*

private val connectionState: MutableStateFlow<ConnectionType> =
    MutableStateFlow(ConnectionType.Init())

fun connectionState(manager: ConnectivityManager): StateFlow<ConnectionType> {

    registerNetwork(manager)

    if (!checkInfoIfFirstLaunchNotConnection(manager)) {
        connectionState.tryEmit(ConnectionType.Lost(true))
    }

    return connectionState.asStateFlow()
}

private fun registerNetwork(manager: ConnectivityManager) =
    manager.registerNetworkCallback(buildNetworkRequest(), object : NetworkCallback() {
        override fun onAvailable(network: Network) {
            super.onAvailable(network)
            manager.bindProcessToNetwork(network)
            connectionState.tryEmit(ConnectionType.Available(true))
        }

        override fun onLost(network: Network) {
            super.onLost(network)
            connectionState.tryEmit(ConnectionType.Lost(true))
        }
    })

private fun buildNetworkRequest(): NetworkRequest = NetworkRequest
    .Builder()
    .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    .addCapability(NetworkCapabilities.NET_CAPABILITY_NOT_RESTRICTED)
    .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
    .addTransportType(NetworkCapabilities.TRANSPORT_ETHERNET)
    .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
    .build()

private fun checkInfoIfFirstLaunchNotConnection(connectivityManager: ConnectivityManager): Boolean {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        val capabilities =
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        if (capabilities != null) {
            when {
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                    return true
                }
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                    return true
                }
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> {
                    return true
                }
            }
        }
    } else {
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        if (activeNetworkInfo != null && activeNetworkInfo.isConnected) {
            return true
        }
    }
    return false
}