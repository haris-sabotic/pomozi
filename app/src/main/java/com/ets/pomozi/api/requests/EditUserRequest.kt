package com.ets.pomozi.api.requests

data class EditUserRequest (
    val name: String?,
    val email: String?,
    val password: String?,
    val phone: String?,
    val about: String?,
    val photo: String?,
)
