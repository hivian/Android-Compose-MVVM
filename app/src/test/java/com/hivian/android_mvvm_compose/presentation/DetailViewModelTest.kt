package com.hivian.android_mvvm_compose.presentation

import com.hivian.android_mvvm_compose.InstantExecutorExtension
import com.hivian.android_mvvm_compose.MainCoroutineExtension
import com.hivian.android_mvvm_compose.domain.mappers.ImageSize
import com.hivian.android_mvvm_compose.domain.mappers.mapToRandomUser
import com.hivian.android_mvvm_compose.data.models.Location
import com.hivian.android_mvvm_compose.data.models.Name
import com.hivian.android_mvvm_compose.data.models.Picture
import com.hivian.android_mvvm_compose.data.models.RandomUserDTO
import com.hivian.android_mvvm_compose.domain.services.application.IRandomUsersService
import com.hivian.android_mvvm_compose.presentation.detail.DetailViewModel
import com.hivian.android_mvvm_compose.ui.services.navigation.INavigationService
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
@ExtendWith(InstantExecutorExtension::class, MainCoroutineExtension::class)
class DetailViewModelTest {

    private val randomUsersService = mock<IRandomUsersService>()
    private val navigationService = mock<INavigationService>()

    private lateinit var viewModel: DetailViewModel

    @BeforeEach
    fun setUp() {
        viewModel = DetailViewModel(0, randomUsersService, navigationService)
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
    fun `Fields are properly initialized`() = runTest {
        val userDTO = RandomUserDTO(
            localId = 0,
            gender = "male",
            name = Name(title = "Mr", first = "Toto", last = "Tutu"),
            email = "toto.tutu@titi.com",
            cell = "0606060606",
            phone = "0101010101",
            picture = Picture.EMPTY,
            location = Location.EMPTY
        )
        val userDomain = userDTO.mapToRandomUser(ImageSize.LARGE)

        whenever(
            randomUsersService.getUserById(0, ImageSize.LARGE)
        ).thenReturn(userDomain)
        viewModel.initialize()
        advanceUntilIdle()
        assertAll("Fields",
            { assertEquals(userDomain.picture, viewModel.picture.value) },
            { assertEquals(userDomain.fullName, viewModel.name.value) },
            { assertEquals(userDomain.email, viewModel.email.value) },
            { assertEquals(userDomain.cell, viewModel.cell.value) },
            { assertEquals(userDomain.phone, viewModel.phone.value) }
        )
    }

}