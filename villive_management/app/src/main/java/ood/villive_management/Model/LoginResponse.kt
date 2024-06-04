package ood.villive_management.Model

data class LoginResponse(
    val token: String,
    val hasBuildingCode: Boolean
)