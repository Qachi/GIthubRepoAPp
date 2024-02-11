package com.example.ktrecyclerview.network

import com.example.ktrecyclerview.model.AndroidGithubResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
interface ApiService {
    @GET("search/repositories")
    suspend fun search(
        @Query("q") query: String,
        @Query("sort") sort: String,
        @Query("order") order: String
    ): Response<AndroidGithubResponse>
}