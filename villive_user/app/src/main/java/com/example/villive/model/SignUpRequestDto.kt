package com.example.villive.model

class SignUpRequestDto(
    var password: String? = null,
    var nickname: String? = null,
    var name: String? = null,
    var role: String = "USER", // 기본값으로 USER 설정
    var member_id: String? = null
)
