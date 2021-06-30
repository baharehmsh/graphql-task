package com.example.graphqlapp.data.mapper

interface Mapper<T,R> {

    fun map(input :T):R

}