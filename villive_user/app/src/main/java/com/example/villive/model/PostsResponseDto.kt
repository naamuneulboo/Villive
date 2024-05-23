package com.example.villive.model


data class PostsResponseDto(
    var id: Long? = null,
    var title: String? = null,
    var contents: String? = null,
    var writer: String? = null,
    var createDate: String? = null,
    var modifiedDate: String? = null,
    var postsLikeCnt: Int? = null,
    var postsLikeCheck: Boolean? = null,
    var category: Category? = null
) {
    enum class Category {
        단체, 공동구매, 민원, 나눔, 동호회
    }
}
