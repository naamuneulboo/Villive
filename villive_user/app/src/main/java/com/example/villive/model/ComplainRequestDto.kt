package com.example.villive.model

enum class ComplainType {
    기계고장,
    공동시설,
    환경개선,
    건의사항,
    기타
}

enum class ComplainStatus {
    접수,
    처리중,
    완료
}

data class ComplainRequestDto(
    val type: String? =null,
    val contents: String? =null,
    val title: String? =null,
    val status: String? =null
)