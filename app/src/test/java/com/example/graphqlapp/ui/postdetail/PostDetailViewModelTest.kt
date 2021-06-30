package com.example.graphqlapp.ui.postdetail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.graphqlapp.data.repository.PostRepository
import com.example.graphqlapp.data.Either
import com.example.graphqlapp.data.model.Post
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
class PostDetailViewModelTest{
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = TestCoroutineDispatcher()

    @MockK
    private lateinit var postsRepository: PostRepository

    private lateinit var detailViewModel: PostDetailViewModel

    @Before
    fun setup() {
        MockKAnnotations.init(this)

        Dispatchers.setMain(testDispatcher)

        detailViewModel = PostDetailViewModel(testDispatcher, postsRepository)
    }

    @Test
    fun `loadDetail  successfully`() = testDispatcher.runBlockingTest {
        coEvery {
            postsRepository.getPost(any())
        } returns Either.Success(POST)

        detailViewModel.postDetailState.observeForever {}
        detailViewModel.loadDetail(POST_ID)

        TestCase.assertEquals(POST, detailViewModel.postDetailState.value?.post)

    }

    @Test
    fun `loadDetail  fails`() = testDispatcher.runBlockingTest {
        coEvery {
            postsRepository.getPost(any())
        } returns Either.Failure(IOException(EXCEPTION_MESSAGE))

        detailViewModel.postDetailState.observeForever {}
        detailViewModel.loadDetail(POST_ID)

        TestCase.assertEquals(EXCEPTION_MESSAGE, detailViewModel.postDetailState.value?.error)

    }

    @After
    fun clear() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

    companion object {
        val POST = Post(id = "A", title = "B", body = "C", user = null)
        const val POST_ID = "A"
        const val EXCEPTION_MESSAGE = "INVALID DATA"
    }
}