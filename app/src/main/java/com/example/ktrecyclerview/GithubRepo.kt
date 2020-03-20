package com.example.ktrecyclerview

data class DataClass(
    val id: Int,
    var name: String,
    val owner: OwnerData,
    val description: String,
    val details: String,
    val created_at: Int,
    val stargazers_count: Int,
    val forks_count: Int,
    val html_url: String


) {
}

data class OwnerData(
    val login: String,
    val id: Int,
    val avatar_url: String
){
}