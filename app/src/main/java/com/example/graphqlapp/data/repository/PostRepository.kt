package com.example.graphqlapp.data.repository

import com.example.graphqlapp.data.mapper.NetworkDetailPostToPost
import com.example.graphqlapp.data.mapper.NetworkPostToPost
import com.example.graphqlapp.data.Either
import com.example.graphqlapp.data.model.Post
import com.example.graphqlapp.remote.network.PostsService
import javax.inject.Inject

class PostRepository @Inject constructor(
    private val postsService: PostsService,
    private val networkPostToPost: NetworkPostToPost,
    private val networkDetailPostToPost: NetworkDetailPostToPost,
) {

    suspend fun getAllPosts(): Either<List<Post>> {
        return when (val result = postsService.getAllPosts()) {
            is Either.Failure -> Either.Failure(result.exception)
            is Either.Success -> Either.Success(networkPostToPost.map(result.data))
        }
    }


    suspend fun getPost(id: String): Either<Post> {
        return when (val result = postsService.getPost(id)) {
            is Either.Failure -> Either.Failure(result.exception)
            is Either.Success -> Either.Success(networkDetailPostToPost.map(result.data))
        }
    }
}