package com.example.ktrecyclerview.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.example.ktrecyclerview.model.Item
import com.example.ktrecyclerview.network.ApiService
import com.example.ktrecyclerview.network.Network
import com.example.ktrecyclerview.repo.GitHubRepoRepository
import kotlinx.coroutines.launch

class GitHubRepoViewModel(application: Application) : AndroidViewModel(application) {

    private val _gitHubItemLiveData: MutableLiveData<List<Item>> = MutableLiveData()
    val gitHubItemLiveData: LiveData<List<Item>> = _gitHubItemLiveData

    private val _isLoading: MutableLiveData<Boolean> = MutableLiveData()
    val isLoading: LiveData<Boolean> = _isLoading




    fun startFetchGithubRepo() {

        _isLoading.value = true
        val service: ApiService = Network.invoke()

        val repo = GitHubRepoRepository(service)

        viewModelScope.launch {
            _gitHubItemLiveData.postValue (
                repo.fetchGitHubRepo (
                    "android+language:java+language:kotlin",
                    "stars", "desc"
                )
            )
            _isLoading.value = false
        }
    }

    /**
     * Factory for constructing AuthViewModel with parameter
     */
    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(GitHubRepoViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return GitHubRepoViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }

}