package com.example.graphqlapp.ui.allposts

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.graphqlapp.domain.PostRepository
import com.example.graphqlapp.model.Either
import com.example.graphqlapp.model.Post
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import junit.framework.TestCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import java.io.IOException

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class AllPostsViewModelTest{

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = TestCoroutineDispatcher()

    private lateinit var allPostsViewModel: AllPostsViewModel

    @MockK
    private lateinit var postsRepository: PostRepository

    @Before
    fun setup() {
        MockKAnnotations.init(this)

        Dispatchers.setMain(testDispatcher)

        coEvery {
            postsRepository.getAllPosts()
        } returns Either.Success(listOf(POST))

        allPostsViewModel = AllPostsViewModel(testDispatcher, postsRepository)
    }

    @After
    fun clear() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun `load successfully`() = testDispatcher.runBlockingTest {
        coEvery {
            postsRepository.getAllPosts()
        } returns Either.Success(listOf(POST))

        allPostsViewModel.state.observeForever {}
        allPostsViewModel.load()

        TestCase.assertEquals(
            listOf(POST),
            (allPostsViewModel.state.value as? AllPostViewState.Success)?.data
        )
    }

    @Test
    fun `load fails`() = testDispatcher.runBlockingTest {
        coEvery {
            postsRepository.getAllPosts()
        } returns Either.Failure(IOException(EXCEPTION_MESSAGE))

        allPostsViewModel.state.observeForever {}
        allPostsViewModel.load()

        TestCase.assertEquals(
            EXCEPTION_MESSAGE,
            (allPostsViewModel.state.value as? AllPostViewState.Failure)?.message
        )
    }

    companion object {
        val POST = Post(id = "A", title = "B", body = "C", user = null)
        const val EXCEPTION_MESSAGE = "INVALID DATA"
    }
}