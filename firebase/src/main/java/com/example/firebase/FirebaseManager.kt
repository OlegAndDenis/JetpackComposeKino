package com.example.firebase

import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import timber.log.Timber

object FirebaseManager {
    private val auth = Firebase.auth

    val authorized
        get() = auth.currentUser != null

    fun createUser(
        email: String,
        password: String,
        onSuccess: ((FirebaseUser) -> Unit) = {},
        onError: ((Exception?) -> Unit) = {}
    ) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    auth.currentUser?.let {
                        Timber.d("createUserWithEmail:success")
                        onSuccess(it)
                    } ?: onError(Exception("User is null"))
                } else {
                    Timber.e(task.exception, "createUserWithEmail:failure")
                    onError(task.exception)
                }
            }
    }

    fun signIn(
        email: String,
        password: String,
        onSuccess: ((FirebaseUser) -> Unit) = {},
        onError: ((Exception?) -> Unit) = {}
    ) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    auth.currentUser?.let {
                        Timber.d("signInWithEmailAndPassword:success")
                        onSuccess(it)
                    } ?: onError(Exception("User is null"))
                } else {
                    Timber.e(task.exception, "signInWithEmailAndPassword:failure")
                    onError(task.exception)
                }
            }
    }

    fun signOut() = auth.signOut()

}

