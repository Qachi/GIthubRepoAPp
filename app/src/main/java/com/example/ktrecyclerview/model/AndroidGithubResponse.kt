package com.example.ktrecyclerview.model


import com.google.gson.annotations.SerializedName

data class AndroidGithubResponse(
    @SerializedName("incomplete_results")
    val incompleteResults: Boolean,
    @SerializedName("items")
    val items: List<Item>,
    @SerializedName("total_count")
    val totalCount: Int
)