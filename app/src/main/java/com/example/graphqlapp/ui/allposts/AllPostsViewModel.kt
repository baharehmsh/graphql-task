package com.example.graphqlapp.ui.allposts

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.graphqlapp.domain.PostRepository
import com.example.graphqlapp.model.Either
import com.example.graphqlapp.model.Post
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AllPostsViewModel  @Inject constructor(private val postsRepository: PostRepository) :
    ViewModel() {


    private val _state = MutableLiveData<AllPostViewState>(null)
    val state: LiveData<AllPostViewState>
        get() = _state

    init {
        load()
    }

    fun load() {
        viewModelScope.launch {

            _state.value = AllPostViewState.Loading
            val result = postsRepository.getAllPosts()
            _state.value = when (result) {
                is Either.Failure -> AllPostViewState.Failure((result).exception.message)
                is Either.Success -> AllPostViewState.Success(result.data)
            }
        }
    }

}