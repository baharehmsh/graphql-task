package com.example.graphqlapp.ui.allposts

import com.example.graphqlapp.model.Post

sealed class AllPostViewState {

    object Loading : AllPostViewState()
    class Success(val data: List<Post>) : AllPostViewState()
    class Failure(val message: String?) : AllPostViewState()
}
