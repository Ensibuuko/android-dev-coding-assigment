package com.ensibuuko.android_dev_coding_assigment.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.domain.model.PostEntity
import com.domain.usecases.posts.FetchRemotePostsUseCase
import com.domain.usecases.posts.GetLocalPostsUseCase
import com.domain.usecases.users.GetLocalSingleUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val fetchRemotePostsUseCase: FetchRemotePostsUseCase,
    private val getLocalPostsUseCase: GetLocalPostsUseCase,
    private val getLocalSingleUserUseCase: GetLocalSingleUserUseCase
) : ViewModel() {

    private val _postListState = MutableLiveData<List<PostEntity>>()
    val postListState get() = _postListState

    fun fetchRemotePosts() {
        viewModelScope.launch {
            fetchRemotePostsUseCase().collect()
        }
    }

    fun getLocalPosts() {
        viewModelScope.launch {
            getLocalPostsUseCase().collect {
                _postListState.value = it
            }
        }
    }

    val getUser = runBlocking { getLocalSingleUserUseCase(219).first() }

}