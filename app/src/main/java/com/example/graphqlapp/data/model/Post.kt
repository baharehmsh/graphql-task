package com.example.graphqlapp.data.model

data class Post(
    val id: String,
    val title: String,
    val body: String,
    val user: User? = null
)

