package com.example.ktrecyclerview

data class DataClass(
    val id: Int,
    var name: String,
    val owner: OwnerData,
    val description: String,
    val details: String,
    val createdAt: Int,
    val stargazersCount: Int,
    val forksCount: Int,
    val htmlUrl: String
) {
}
data class OwnerData(
    val login: String,
    val id: Int,
    val avatarUrl: String
){
}