package com.hivian.lydia_test.presentation

import com.hivian.lydia_test.InstantExecutorExtension
import com.hivian.lydia_test.MainCoroutineExtension
import com.hivian.lydia_test.data.mappers.ImageSize
import com.hivian.lydia_test.data.mappers.mapToRandomUser
import com.hivian.lydia_test.data.models.dto.Location
import com.hivian.lydia_test.data.models.dto.Name
import com.hivian.lydia_test.data.models.dto.Picture
import com.hivian.lydia_test.data.models.dto.RandomUserDTO
import com.hivian.lydia_test.data.services.application.IRandomUsersService
import com.hivian.lydia_test.ui.services.navigation.INavigationService
import com.hivian.lydia_test.presentation.detail.DetailViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.kotlin.*

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