package com.example.graphqlapp.domain.mapper

import com.example.graphqlapp.AllPostsQuery
import com.example.graphqlapp.model.Post
import javax.inject.Inject

class NetworkPostToPost @Inject constructor() : Mapper<AllPostsQuery.Posts, List<Post>> {
    override fun map(input: AllPostsQuery.Posts): List<Post> {
        val output = mutableListOf<Post>()
        input.data?.forEach {
            if (it != null && !it.id.isNullOrEmpty() && !it.title.isNullOrEmpty()) {
                val post = Post(it.id, it.title, it.body ?: "")
                output.add(post)
            }

        }
        return output
    }
}