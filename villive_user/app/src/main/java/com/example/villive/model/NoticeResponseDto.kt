package com.example.villive.model

import android.os.Parcel
import android.os.Parcelable

data class NoticeResponseDto(
    var id: Long? = null,
    var title: String? = null,
    var contents: String? = null,
    var writer: String? = null,
    var createDate: String? = null,
)