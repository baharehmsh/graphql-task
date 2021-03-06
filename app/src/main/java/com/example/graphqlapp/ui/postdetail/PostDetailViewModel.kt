package com.example.graphqlapp.ui.postdetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.graphqlapp.data.repository.PostRepository
import com.example.graphqlapp.data.Either
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostDetailViewModel @Inject constructor(
    mainDispatcher : CoroutineDispatcher,
    private val postsRepository: PostRepository
) :
    ViewModel() {

    private val job = SupervisorJob()
    private val uiScope = CoroutineScope(mainDispatcher + job)

    private val _postDetailState = MutableLiveData(DetailPostViewState())
    val postDetailState: LiveData<DetailPostViewState>
        get() = _postDetailState


    fun loadDetail(id: String) {
        uiScope.launch {
            _postDetailState.value = _postDetailState.value?.copy(
                isLoading = true,
                error = null,
            )

            val result = postsRepository.getPost(id)

            _postDetailState.value = when (result) {
                is Either.Failure -> _postDetailState.value?.copy(
                    isLoading = false,
                    error = result.exception.message
                )
                is Either.Success -> _postDetailState.value?.copy(
                    isLoading = false,
                    post = result.data,
                    error = null,
                )
            }
        }
    }
    override fun onCleared() {
        super.onCleared()
        this.job.cancel()
    }
}