package com.example.villive.model
data class CommentRequestDto(
    val content: String?=null,
    val writer: String?=null // 댓글을 작성한 사용자의 정보 (예: 사용자명)
)