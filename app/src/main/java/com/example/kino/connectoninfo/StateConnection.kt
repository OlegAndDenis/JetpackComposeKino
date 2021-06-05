package com.example.kino.connectoninfo

import android.content.Context
import android.net.*
import android.net.ConnectivityManager.NetworkCallback
import android.os.Build
import com.example.kino.connectoninfo.model.ConnectionType
import kotlinx.coroutines.flow.*

private val connectionListener: MutableStateFlow<ConnectionType> =
    MutableStateFlow(ConnectionType.Init())

fun connectionState(context: Context): StateFlow<ConnectionType> {
    val manager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    registerNetwork(manager)
    if (!isFirstLaunch(manager)) {
        connectionListener.tryEmit(ConnectionType.Lost(true))
    }
    return connectionListener.asStateFlow()
}

fun registerNetwork(manager: ConnectivityManager) =
    manager.registerNetworkCallback(buildNetworkRequest(), object : NetworkCallback() {
        override fun onAvailable(network: Network) {
            super.onAvailable(network)
            manager.bindProcessToNetwork(network)
            connectionListener.tryEmit(ConnectionType.Available(true))
        }

        override fun onLost(network: Network) {
            super.onLost(network)
            connectionListener.tryEmit(ConnectionType.Lost(true))
        }
    })

fun buildNetworkRequest(): NetworkRequest = NetworkRequest
    .Builder()
    .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    .addCapability(NetworkCapabilities.NET_CAPABILITY_NOT_RESTRICTED)
    .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
    .addTransportType(NetworkCapabilities.TRANSPORT_ETHERNET)
    .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
    .build()

fun isFirstLaunch(connectivityManager: ConnectivityManager): Boolean {
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