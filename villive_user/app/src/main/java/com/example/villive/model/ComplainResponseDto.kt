package com.example.villive.model


data class ComplainResponseDto(
    var id: Long? = null,
    var title: String? = null,
    var contents: String? = null,
    var writer: String? = null,
    var createdDate: String? = null,
    var status: Status? = null,
    var type: Type? = null
) {
    enum class Status {
        접수, 처리중, 완료
    }

    enum class Type {
        기계고장, 공동시설, 환경개선, 건의사항, 기타
    }
}