package com.example.graphqlapp.ui.postdetail

import com.example.graphqlapp.data.model.Post

data class DetailPostViewState(
    val isLoading: Boolean = false,
    val post: Post? = null,
    val error: String? = null,
)
