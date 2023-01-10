package com.hivian.lydia_test.presentation

import com.hivian.lydia_test.InstantExecutorExtension
import com.hivian.lydia_test.MainCoroutineExtension
import com.hivian.lydia_test.core.models.ImageSize
import com.hivian.lydia_test.core.models.Mapper
import com.hivian.lydia_test.core.models.dto.Location
import com.hivian.lydia_test.core.models.dto.Name
import com.hivian.lydia_test.core.models.dto.Picture
import com.hivian.lydia_test.core.models.dto.RandomUserDTO
import com.hivian.lydia_test.core.services.database.IDatabaseService
import com.hivian.lydia_test.core.services.navigation.INavigationService
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

    private val databaseService = mock<IDatabaseService>()
    private val navigationService = mock<INavigationService>()

    private lateinit var viewModel: DetailViewModel

    @BeforeEach
    fun setUp() {
        viewModel = DetailViewModel(0, databaseService, navigationService)
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
        val userDomain = Mapper.mapDTOToDomain(userDTO, ImageSize.LARGE)

        whenever(
            databaseService.getUserById(0)
        ).thenReturn(userDTO)
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