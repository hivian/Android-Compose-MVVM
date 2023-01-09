package com.hivian.lydia_test.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.hivian.lydia_test.MainCoroutineRule
import com.hivian.lydia_test.core.data.ErrorType
import com.hivian.lydia_test.core.data.ServiceResult
import com.hivian.lydia_test.core.models.domain.RandomUserDomain
import com.hivian.lydia_test.core.models.dto.Location
import com.hivian.lydia_test.core.models.dto.Name
import com.hivian.lydia_test.core.models.dto.Picture
import com.hivian.lydia_test.core.models.dto.RandomUserDTO
import com.hivian.lydia_test.core.services.application.IRandomUsersService
import com.hivian.lydia_test.core.services.localization.ILocalizationService
import com.hivian.lydia_test.core.services.navigation.INavigationService
import com.hivian.lydia_test.core.services.userinteraction.IUserInteractionService
import com.hivian.lydia_test.presentation.home.HomeViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.withContext
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.*

@ExperimentalCoroutinesApi
class HomeViewModelTest {

    @get:Rule
    val testRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private val localizationService = mock<ILocalizationService>()
    private val navigationService = mock<INavigationService>()
    private val randomUsersService = mock<IRandomUsersService>()
    private val userInteractionService = mock<IUserInteractionService>()

    private lateinit var viewModel: HomeViewModel

    @Before
    fun setUp() {
        viewModel = HomeViewModel(localizationService, navigationService, randomUsersService, userInteractionService)
    }

    @Test
    fun `ViewModel is initialized`() {
        viewModel.initialize()
        Assert.assertEquals(true, viewModel.isInitialized.value)
    }

    @Test
    fun `ViewModel is not initialized`() {
        Assert.assertEquals(false, viewModel.isInitialized.value)
    }

    @Test
    fun `Success state works`() = runTest {
        whenever(
            randomUsersService.fetchRandomUsers(HomeViewModel.PAGINATOR_INITIAL_KEY, HomeViewModel.RESULT_COUNT)
        ).thenReturn(
            ServiceResult.Success(emptyList())
        )
        viewModel.initialize()
        advanceUntilIdle()
        Assert.assertEquals(ViewModelVisualState.Success, viewModel.viewModelVisualState.value)
    }

    @Test
    fun `Failure state works`() = runTest {
        whenever(
            randomUsersService.fetchRandomUsers(HomeViewModel.PAGINATOR_INITIAL_KEY, HomeViewModel.RESULT_COUNT)
        ).thenReturn(
            ServiceResult.Error(ErrorType.UNKNOWN, emptyList())
        )
        viewModel.initialize()
        advanceUntilIdle()
        Assert.assertEquals(ViewModelVisualState.Error(ErrorType.UNKNOWN), viewModel.viewModelVisualState.value)
    }

    @Test
    fun `Initial Loading state works`() = runTest {
        whenever(
            randomUsersService.fetchRandomUsers(HomeViewModel.PAGINATOR_INITIAL_KEY, HomeViewModel.RESULT_COUNT)
        ).doSuspendableAnswer {
            withContext(Dispatchers.IO) { delay(1000) }
            ServiceResult.Success(emptyList())
        }
        viewModel.initialize()

        advanceUntilIdle()
        Assert.assertEquals(ViewModelVisualState.Loading, viewModel.viewModelVisualState.value)
        Assert.assertEquals(false, viewModel.showLoadMoreLoader.value)
    }

    @Test
    fun `Load more Loading state works`() = runTest {
        whenever(
            randomUsersService.fetchRandomUsers(HomeViewModel.PAGINATOR_INITIAL_KEY, HomeViewModel.RESULT_COUNT)
        ).thenReturn(
            ServiceResult.Success(emptyList())
        )
        whenever(
            randomUsersService.fetchRandomUsers(HomeViewModel.PAGINATOR_INITIAL_KEY + 1, HomeViewModel.RESULT_COUNT)
        ).doSuspendableAnswer {
            withContext(Dispatchers.IO) { delay(1000) }
            ServiceResult.Success(emptyList())
        }
        viewModel.initialize()
        viewModel.loadNextItem()
        advanceUntilIdle()
        Assert.assertEquals(ViewModelVisualState.Success, viewModel.viewModelVisualState.value)
        Assert.assertEquals(true, viewModel.showLoadMoreLoader.value)
    }

    @Test
    fun `Items are initialized with correct data`() = runTest {
        val id = 0
        val gender = "male"
        val title  = "Mr"
        val firstName = "Toto"
        val lastName = "Tutu"
        val email = "toto.tutu@titi.com"
        val cell = "0606060606"
        val phone = "0101010101"
        val picture = Picture.EMPTY
        val location = Location.EMPTY

        whenever(
            randomUsersService.fetchRandomUsers(HomeViewModel.PAGINATOR_INITIAL_KEY, HomeViewModel.RESULT_COUNT)
        ).thenReturn(
            ServiceResult.Success(listOf(
                RandomUserDTO(
                    localId = id,
                    gender = gender,
                    name = Name(title = title, first = firstName, last = lastName),
                    email = email,
                    cell = cell,
                    phone = phone,
                    picture = picture,
                    location = location
                )
            ))
        )
        viewModel.initialize()
        advanceUntilIdle()
        Assert.assertEquals(viewModel.items.toList(), listOf(
            RandomUserDomain(
                id = id,
                gender = gender,
                firstName = firstName,
                lastName = lastName,
                email = email,
                phone = phone,
                cell = cell,
                picture = ""
            )
        ))
    }

    @Test
    fun `Items are updated with correct data`() = runTest {
        val id = 0
        val gender = "male"
        val title  = "Mr"
        val firstName = "Toto"
        val lastName = "Tutu"
        val email = "toto.tutu@titi.com"
        val cell = "0606060606"
        val phone = "0101010101"
        val picture = Picture.EMPTY
        val location = Location.EMPTY

        repeat(2) { index ->
            whenever(
                randomUsersService.fetchRandomUsers(HomeViewModel.PAGINATOR_INITIAL_KEY + index, HomeViewModel.RESULT_COUNT)
            ).thenReturn(
                ServiceResult.Success(listOf(
                    RandomUserDTO(
                        localId = id + index,
                        gender = gender,
                        name = Name(title = title, first = firstName, last = lastName),
                        email = email,
                        cell = cell,
                        phone = phone,
                        picture = picture,
                        location = location
                    )
                ))
            )
        }
        viewModel.initialize()
        viewModel.loadNextItem()
        advanceUntilIdle()
        Assert.assertEquals(viewModel.items.toList(), listOf(
            RandomUserDomain(
                id = id,
                gender = gender,
                firstName = firstName,
                lastName = lastName,
                email = email,
                phone = phone,
                cell = cell,
                picture = ""
            ),
            RandomUserDomain(
                id = id + 1,
                gender = gender,
                firstName = firstName,
                lastName = lastName,
                email = email,
                phone = phone,
                cell = cell,
                picture = ""
            )
        ))
    }

}