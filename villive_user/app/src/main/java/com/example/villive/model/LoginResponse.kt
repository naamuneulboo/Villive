package com.example.villive.model

data class LoginResponse(
    val token: String,
    val hasBuildingCode: Boolean
)