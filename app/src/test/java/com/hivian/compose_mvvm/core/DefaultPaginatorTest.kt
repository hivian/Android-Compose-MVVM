package com.hivian.compose_mvvm.core

import com.hivian.compose_mvvm.InstantExecutorExtension
import com.hivian.compose_mvvm.MainCoroutineExtension
import com.hivian.compose_mvvm.core.data.ServiceResult
import com.hivian.compose_mvvm.core.data.paginator.DefaultPaginator
import com.hivian.compose_mvvm.domain.models.RandomUser
import com.hivian.compose_mvvm.data.models.Picture
import com.hivian.compose_mvvm.presentation.home.HomeViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExperimentalCoroutinesApi
@ExtendWith(InstantExecutorExtension::class, MainCoroutineExtension::class)
class DefaultPaginatorTest {

    @Test
    fun `Paginator initialization works`() = runTest {
        val id = 0
        val gender = "male"
        val firstName = "Toto"
        val lastName = "Tutu"
        val email = "toto.tutu@titi.com"
        val cell = "0606060606"
        val phone = "0101010101"
        val picture = Picture.EMPTY

        val user = RandomUser(id, gender, firstName, lastName, email, phone, cell, picture.large)

        DefaultPaginator(
            initialKey = HomeViewModel.PAGINATOR_INITIAL_KEY,
            getNextKey = { currentKey -> currentKey + 1 },
            onRequest = { nextPage -> ServiceResult.Success(listOf(user)) },
            onLoading = { initialLoad ->
                assertTrue(initialLoad)
            },
            onError = { errorType, users, initialLoad ->
                fail("Error callback should not be called")
            },
            onSuccess = { users, initialLoad ->
                assertTrue(initialLoad)
            }
        ).loadNextItems()
    }

    @Test
    fun `Paginator is reset`() = runTest {
        val paginator = DefaultPaginator(
            initialKey = HomeViewModel.PAGINATOR_INITIAL_KEY,
            getNextKey = { currentKey -> currentKey + 1 },
            onRequest = { nextPage -> ServiceResult.Success(emptyList<RandomUser>()) },
            onLoading = { initialLoad ->
                assertTrue(initialLoad)
            },
            onError = { errorType, users, initialLoad ->
                fail("Error callback should not be called")
            },
            onSuccess = { users, initialLoad ->
                assertTrue(initialLoad)
            }
        )

        paginator.loadNextItems()
        assertEquals(HomeViewModel.PAGINATOR_INITIAL_KEY + 1, paginator.currentKey)
        paginator.reset()
        assertEquals(HomeViewModel.PAGINATOR_INITIAL_KEY, paginator.currentKey)
    }

}
