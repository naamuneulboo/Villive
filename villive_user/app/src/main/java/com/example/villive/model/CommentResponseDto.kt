package com.example.villive.model


data class CommentResponseDto(
    val id: Long,
    val content: String,
    val writer: String,
    val createdDate: String
)