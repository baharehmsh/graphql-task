package com.example.graphqlapp.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.graphqlapp.AllPostsQuery
import com.example.graphqlapp.PostQuery
import com.example.graphqlapp.data.mapper.NetworkDetailPostToPost
import com.example.graphqlapp.data.mapper.NetworkPostToPost
import com.example.graphqlapp.data.model.Post
import com.example.graphqlapp.data.repository.PostRepository
import com.example.graphqlapp.remote.network.PostsService
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import junit.framework.TestCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import java.io.IOException

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class PostRepositoryTest  :TestCase(){

    private lateinit var postsRepository: PostRepository

    @MockK
    private lateinit var postsService: PostsService

    @MockK
    private lateinit var networkPostToPost: NetworkPostToPost

    @MockK
    private lateinit var networkDetailPostToPost: NetworkDetailPostToPost

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        MockKAnnotations.init(this)

        postsRepository = PostRepository(
            postsService,
            networkPostToPost,
            networkDetailPostToPost
        )
    }

    @Test
    fun `getAllPosts returns success`() = runBlockingTest{
        coEvery {
            postsService.getAllPosts()
        } returns Either.Success(
            AllPostsQuery.Posts(
                data = listOf(),
                meta = null,
                links = null
            )
        )

        coEvery {
            networkPostToPost.map(any())
        } returns listOf(POST)

        val result = postsRepository.getAllPosts()

        assertEquals(POST, (result as Either.Success).data.first())

    }

    @Test
    fun `getAllPosts returns failure`() = runBlockingTest {
        coEvery {
            postsService.getAllPosts()
        } returns Either.Failure(IOException())

        val result = postsRepository.getAllPosts()

        assertEquals(IOException::class.java, (result as Either.Failure).exception::class.java)

    }

    @Test
    fun `getPost returns success`() = runBlockingTest {
        coEvery {
            postsService.getPost(any())
        } returns Either.Success(
            PostQuery.Post(__typename = "", id = null, title = null, body = null, user = null)
        )

        coEvery {
            networkDetailPostToPost.map(any())
        } returns POST

        val result = postsRepository.getPost(POST_ID)

        assertEquals(POST, (result as Either.Success).data)

    }

    @Test
    fun `getPost returns failure`() = runBlockingTest {
        coEvery {
            postsService.getPost(any())
        } returns Either.Failure(IOException())

        val result = postsRepository.getPost(POST_ID)

        assertEquals(IOException::class.java, (result as Either.Failure).exception::class.java)

    }

    companion object {
        val POST = Post(id = "A", title = "B", body = "C", user = null)
        const val POST_ID = "A"
    }
}