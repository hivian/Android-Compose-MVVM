package com.hivian_compose_mvvm.basic_feature.presentation

import com.hivian.compose_mvvm.core.InstantExecutorExtension
import com.hivian.compose_mvvm.core.datasources.ServiceResult
import com.hivian.compose_mvvm.core.datasources.models.Location
import com.hivian.compose_mvvm.core.datasources.models.Name
import com.hivian.compose_mvvm.core.datasources.models.Picture
import com.hivian.compose_mvvm.core.datasources.models.RandomUserDTO
import com.hivian_compose_mvvm.basic_feature.data.mappers.ImageSize
import com.hivian_compose_mvvm.basic_feature.data.mappers.mapToRandomUser
import com.hivian_compose_mvvm.basic_feature.domain.usecases.GetRandomUserByIdUseCase
import com.hivian_compose_mvvm.basic_feature.domain.usecases.NavigateBackUseCase
import com.hivian_compose_mvvm.basic_feature.domain.usecases.ShowAppMessageUseCase
import com.hivian_compose_mvvm.basic_feature.domain.usecases.TranslateResourceUseCase
import com.hivian_compose_mvvm.basic_feature.presentation.detail.DetailViewModel
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
@ExtendWith(InstantExecutorExtension::class, com.hivian.compose_mvvm.core.MainCoroutineExtension::class)
class DetailViewModelTest {

    private val translateResourceUseCase = mock<TranslateResourceUseCase>()
    private val getRandomUserByIdUseCase = mock<GetRandomUserByIdUseCase>()
    private val showAppMessageUseCase = mock<ShowAppMessageUseCase>()
    private val navigateBackUseCase = mock<NavigateBackUseCase>()

    private lateinit var viewModel: DetailViewModel

    @BeforeEach
    fun setUp() {
        viewModel = DetailViewModel(
            0,
            translateResourceUseCase,
            getRandomUserByIdUseCase,
            showAppMessageUseCase,
            navigateBackUseCase
        )
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
            getRandomUserByIdUseCase(0)
        ).thenReturn(ServiceResult.Success(userDomain))
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