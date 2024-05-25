package com.example.villive.model
data class UpdatePwdRequestDto(
    val currentPassword: String?=null,
    val newPassword: String?=null
)
