package ood.villive_management

import ood.villive_management.Model.ComplainResponseDto

fun getStatusFromString(statusString: String): ComplainResponseDto.Status? {
    return try {
        ComplainResponseDto.Status.valueOf(statusString)
    } catch (e: IllegalArgumentException) {
        null
    }
}