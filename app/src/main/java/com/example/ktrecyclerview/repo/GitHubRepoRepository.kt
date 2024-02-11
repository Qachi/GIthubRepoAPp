package com.example.ktrecyclerview.repo

import com.example.ktrecyclerview.model.Item
import com.example.ktrecyclerview.network.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GitHubRepoRepository(private val apiService: ApiService) {
    suspend fun fetchGitHubRepo(q: String, sort: String, order: String): List<Item>? =
        withContext(Dispatchers.IO) {
            println("github repo here")

            try {
                val result = apiService.search(q, sort, order)
                if (result.isSuccessful) {
                    result.body()?.items
                } else null
            }catch (e: Throwable){
                null
            }
        }
}