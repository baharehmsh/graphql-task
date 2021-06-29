package com.example.graphqlapp.network

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Input
import com.apollographql.apollo.coroutines.await
import com.apollographql.apollo.exception.ApolloException
import com.example.graphqlapp.AllPostsQuery
import com.example.graphqlapp.PostQuery
import com.example.graphqlapp.model.Either
import javax.inject.Inject

class PostsService @Inject constructor(
    private val apolloClient: ApolloClient
) {


    suspend fun getAllPosts(): Either<AllPostsQuery.Posts> {
        return try {
            val response = apolloClient.query(AllPostsQuery()).await()
            val posts = response.data?.posts
            if (posts == null || response.hasErrors()) {
                val message = response.errors?.joinToString { it.message } ?: "Invalid response"
                return Either.Failure(Exception(message))
            }
            Either.Success(posts)

        } catch (e: ApolloException) {
            Either.Failure(e)
        }
    }

    suspend fun getPost(id: String): Either<PostQuery.Post> {
        return try {
            val response = apolloClient.query(PostQuery(id)).await()
            val post = response.data?.post
            if (post == null || response.hasErrors()) {
                val message = response.errors?.joinToString { it.message } ?: "Invalid response"
                return Either.Failure(Exception(message))
            }
            return Either.Success(post)
        } catch (e: ApolloException) {
            Either.Failure(e)
        }
    }
}