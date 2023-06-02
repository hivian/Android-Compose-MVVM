package com.hivian.compose_mvvm.presentation

import com.hivian.compose_mvvm.InstantExecutorExtension
import com.hivian.compose_mvvm.MainCoroutineExtension
import com.hivian.compose_mvvm.data.mappers.ImageSize
import com.hivian.compose_mvvm.data.mappers.mapToRandomUsers
import com.hivian.compose_mvvm.data.models.Location
import com.hivian.compose_mvvm.data.models.Name
import com.hivian.compose_mvvm.data.models.Picture
import com.hivian.compose_mvvm.data.models.RandomUserDTO
import com.hivian.compose_mvvm.data.sources.remote.ErrorType
import com.hivian.compose_mvvm.domain.repository.ServiceResult
import com.hivian.compose_mvvm.domain.services.ILocalizationService
import com.hivian.compose_mvvm.domain.usecases.GetRandomUsersUseCase
import com.hivian.compose_mvvm.domain.usecases.NavigateToRandomUserDetailUseCase
import com.hivian.compose_mvvm.domain.usecases.ShowAppMessageUseCase
import com.hivian.compose_mvvm.presentation.base.ViewModelVisualState
import com.hivian.compose_mvvm.presentation.home.HomeViewModel
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
    private val navigateToRandomUserDetailUseCase = mock<NavigateToRandomUserDetailUseCase>()
    private val randomUsersUseCase = mock<GetRandomUsersUseCase>()
    private val showAppMessageUseCase = mock<ShowAppMessageUseCase>()

    private lateinit var viewModel: HomeViewModel

    @BeforeEach
    fun setUp() {
        viewModel = HomeViewModel(
            localizationService,
            navigateToRandomUserDetailUseCase,
            randomUsersUseCase,
            showAppMessageUseCase)
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
            randomUsersUseCase(HomeViewModel.PAGINATION_INITIAL_KEY, HomeViewModel.RESULT_COUNT)
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
            randomUsersUseCase(HomeViewModel.PAGINATION_INITIAL_KEY, HomeViewModel.RESULT_COUNT)
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
            randomUsersUseCase(HomeViewModel.PAGINATION_INITIAL_KEY, HomeViewModel.RESULT_COUNT)
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
            randomUsersUseCase(HomeViewModel.PAGINATION_INITIAL_KEY, HomeViewModel.RESULT_COUNT)
        ).thenReturn(
            ServiceResult.Success(emptyList())
        )
        whenever(
            randomUsersUseCase(HomeViewModel.PAGINATION_INITIAL_KEY + 1, HomeViewModel.RESULT_COUNT)
        ).doSuspendableAnswer {
            withContext(Dispatchers.IO) { delay(1000) }
            ServiceResult.Success(emptyList())
        }
        viewModel.initialize()
        viewModel.loadNext()
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
            randomUsersUseCase(HomeViewModel.PAGINATION_INITIAL_KEY, HomeViewModel.RESULT_COUNT)
        ).thenReturn(
            ServiceResult.Success(usersDomain)
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
                randomUsersUseCase(HomeViewModel.PAGINATION_INITIAL_KEY + index, HomeViewModel.RESULT_COUNT)
            ).thenReturn(
                ServiceResult.Success(listOf(usersDTO[index]).mapToRandomUsers(ImageSize.MEDIUM))
            )
        }

        val usersDomain = usersDTO.mapToRandomUsers(ImageSize.MEDIUM)

        viewModel.initialize()
        viewModel.loadNext()
        advanceUntilIdle()
        assertEquals(usersDomain, viewModel.items.toList())
    }

}