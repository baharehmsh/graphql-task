package com.example.graphqlapp.domain.mapper

import com.example.graphqlapp.PostQuery
import com.example.graphqlapp.model.Post
import com.example.graphqlapp.model.User

class NetworkDetailPostToPost : Mapper<PostQuery.Post, Post> {
    override fun map(input: PostQuery.Post): Post {


        return Post(
            input.id.toString(),
            input.title.toString(),
            input.body.toString(),
            NetworkUserToUser().map(input.user)
        )
    }
}

class NetworkUserToUser : Mapper<PostQuery.User?, User> {
    override fun map(input: PostQuery.User?): User {
        return User(input?.id.toString(), input?.name.toString(), input?.username.toString())
    }
}