package com.hivian.compose_mvvm.presentation

import com.hivian.compose_mvvm.InstantExecutorExtension
import com.hivian.compose_mvvm.MainCoroutineExtension
import com.hivian.compose_mvvm.core.base.ViewModelVisualState
import com.hivian.compose_mvvm.domain.repository.ServiceResult
import com.hivian.compose_mvvm.data.sources.remote.ErrorType
import com.hivian.compose_mvvm.core.services.localization.ILocalizationService
import com.hivian.compose_mvvm.core.services.userinteraction.IUserInteractionService
import com.hivian.compose_mvvm.data.mappers.ImageSize
import com.hivian.compose_mvvm.data.mappers.mapToRandomUsers
import com.hivian.compose_mvvm.data.models.Location
import com.hivian.compose_mvvm.data.models.Name
import com.hivian.compose_mvvm.data.models.Picture
import com.hivian.compose_mvvm.data.models.RandomUserDTO
import com.hivian.compose_mvvm.domain.repository.IRandomUsersRepository
import com.hivian.compose_mvvm.presentation.home.HomeViewModel
import com.hivian.compose_mvvm.presentation.services.navigation.INavigationService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.withContext
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.kotlin.doSuspendableAnswer
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
@ExtendWith(InstantExecutorExtension::class, MainCoroutineExtension::class)
class HomeViewModelTest {

    private val localizationService = mock<ILocalizationService>()
    private val navigationService = mock<INavigationService>()
    private val randomUsersService = mock<IRandomUsersRepository>()
    private val userInteractionService = mock<IUserInteractionService>()

    private lateinit var viewModel: HomeViewModel

    @BeforeEach
    fun setUp() {
        viewModel = HomeViewModel(localizationService, navigationService, randomUsersService, userInteractionService)
    }

    @Test
    fun `ViewModel is initialized`() {
        viewModel.initialize()
        assertEquals(true, viewModel.isInitialized.value)
    }

    @Test
    fun `ViewModel is not initialized`() {
        assertEquals(false, viewModel.isInitialized.value)
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
        assertEquals(ViewModelVisualState.Success, viewModel.viewModelVisualState.value)
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
        assertEquals(ViewModelVisualState.Error(ErrorType.UNKNOWN), viewModel.viewModelVisualState.value)
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
        assertAll("Initial loader is loading",
            { assertEquals(ViewModelVisualState.Loading, viewModel.viewModelVisualState.value) },
            { assertEquals(false, viewModel.showLoadMoreLoader.value) }
        )
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
        assertAll("Load more loader is loading",
            { assertEquals(ViewModelVisualState.Success, viewModel.viewModelVisualState.value) },
            { assertEquals(true, viewModel.showLoadMoreLoader.value) }
        )
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

        val usersDTO = listOf(
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
        )
        val usersDomain = usersDTO.mapToRandomUsers(ImageSize.MEDIUM)

        whenever(
            randomUsersService.fetchRandomUsers(HomeViewModel.PAGINATOR_INITIAL_KEY, HomeViewModel.RESULT_COUNT)
        ).thenReturn(
            ServiceResult.Success(usersDTO)
        )
        viewModel.initialize()
        advanceUntilIdle()
        assertEquals(usersDomain, viewModel.items.toList())
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

        val usersDTO = ArrayList<RandomUserDTO>()
        repeat(2) { index ->
            usersDTO.addAll(listOf(
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
        }
        repeat(2) { index ->
            whenever(
                randomUsersService.fetchRandomUsers(HomeViewModel.PAGINATOR_INITIAL_KEY + index, HomeViewModel.RESULT_COUNT)
            ).thenReturn(
                ServiceResult.Success(listOf(usersDTO[index]))
            )
        }

        val usersDomain = usersDTO.mapToRandomUsers(ImageSize.MEDIUM)

        viewModel.initialize()
        viewModel.loadNextItem()
        advanceUntilIdle()
        assertEquals(usersDomain, viewModel.items.toList())
    }

}