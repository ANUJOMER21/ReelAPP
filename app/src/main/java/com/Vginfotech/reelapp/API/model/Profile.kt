package com.Vginfotech.reelapp.API.model

data class Profile(
    val categories: List<CategoryX>,
    val email: String,
    val gender: String,
    val name: String,
    val profile_pic: String,
    val user_id: Int
)