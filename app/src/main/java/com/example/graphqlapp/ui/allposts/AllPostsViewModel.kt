package com.example.graphqlapp.ui.allposts

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.graphqlapp.data.repository.PostRepository
import com.example.graphqlapp.data.Either
import com.example.graphqlapp.data.model.Post
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AllPostsViewModel  @Inject constructor(
    mainDispatcher : CoroutineDispatcher,
    private val postsRepository: PostRepository
) :
    ViewModel() {

    private val job = SupervisorJob()
    private val uiScope = CoroutineScope(mainDispatcher + job)

    private val _state = MutableLiveData<AllPostViewState>(null)
    val state: LiveData<AllPostViewState>
        get() = _state

    private val _navigateToDetailFragment = MutableLiveData<Post>()
    val navigateToDetailFragment: LiveData<Post>
        get() = _navigateToDetailFragment

    init {
        load()
    }

    fun load() {
        uiScope.launch {

            _state.value = AllPostViewState.Loading
            val result = postsRepository.getAllPosts()
            _state.value = when (result) {
                is Either.Failure -> AllPostViewState.Failure((result).exception.message)
                is Either.Success -> AllPostViewState.Success(result.data)
            }
        }
    }

    fun displayDetailPost(post: Post) {
        _navigateToDetailFragment.value = post
    }

    fun doneNavigating() {
        _navigateToDetailFragment.value = null
    }

    override fun onCleared() {
        super.onCleared()
        this.job.cancel()
    }
}