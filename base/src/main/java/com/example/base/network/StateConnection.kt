package com.example.base.network

import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Build
import com.example.base.network.model.ConnectionType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

private val connectionListener: MutableStateFlow<ConnectionType> =
    MutableStateFlow(ConnectionType.Init())

private val networkRequest: NetworkRequest = NetworkRequest
    .Builder()
    .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    .addCapability(NetworkCapabilities.NET_CAPABILITY_NOT_RESTRICTED)
    .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
    .addTransportType(NetworkCapabilities.TRANSPORT_ETHERNET)
    .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
    .build()

/**
 * This method checked by first launch network state and
 * return stateFlow with info by connection type
 */
fun connectionState(manager: ConnectivityManager): StateFlow<ConnectionType> {

    registerNetwork(manager)

    if (!checkInfoIfFirstLaunchNotConnection(manager)) {
        connectionListener.tryEmit(ConnectionType.Lost(true))
    }

    return connectionListener.asStateFlow()
}

/**
 * This method subscribe to network state and update emitting connections state for observable
 */
private fun registerNetwork(manager: ConnectivityManager) =
    manager.registerNetworkCallback(
        networkRequest,
        object : ConnectivityManager.NetworkCallback() {
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

/**
 * This method checked network by first launch app.
 * Since the registerNetworkCallback is not working if first launch no network.
 */
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