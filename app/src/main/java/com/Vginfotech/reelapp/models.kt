package com.Vginfotech.reelapp

import java.util.*

enum class Icon {
    CAMERA,
    PROFILE,
    SHARE,
    AUDIO,
    LIKE,

}

data class Reel(
    val reelUrl: String,
    val isFollowed: Boolean,
    val reelInfo: ReelInfo
)

data class ReelInfo(
    val username: String,
    val profilePicUrl: String,
    val description: String? = null,
    val isLiked: Boolean,
    val likes: Int,
    val comments: Int,
    val audio: String = "$username • Original Audio",
    val audioPicUrl: String = profilePicUrl,
    val filter: String? = null,
    val location: String? = null,
    val taggedPeople: List<String>? = null,
    val id: String = UUID.randomUUID().toString()
)