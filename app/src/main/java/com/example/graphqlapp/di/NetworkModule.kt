package com.example.graphqlapp.di

import com.apollographql.apollo.ApolloClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    fun providesApolloClient(): ApolloClient {
        return ApolloClient.builder()
            .serverUrl("https://graphqlzero.almansi.me/api")
            .build()
    }
}