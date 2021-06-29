package com.example.graphqlapp.domain.mapper

interface Mapper<T,R> {

    fun map(input :T):R

}