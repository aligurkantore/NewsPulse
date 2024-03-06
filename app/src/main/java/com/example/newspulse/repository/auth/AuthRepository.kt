package com.example.newspulse.repository.auth

import com.example.newspulse.data.remote.Auth
import com.example.newspulse.utils.Resource

interface AuthRepository {

    suspend fun login(email: String, password: String): Resource<Auth>
    suspend fun register(name: String, email: String, password: String): Resource<Auth>
}