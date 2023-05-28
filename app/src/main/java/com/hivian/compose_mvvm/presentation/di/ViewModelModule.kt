package com.hivian.compose_mvvm.presentation.di

import androidx.lifecycle.SavedStateHandle
import com.hivian.compose_mvvm.ui.services.navigation.routes.NavScreen
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class UserId

@Module
@InstallIn(ViewModelComponent::class)
object ViewModelModule {

    @Provides
    @UserId
    @ViewModelScoped
    fun provideUserId(savedStateHandle: SavedStateHandle): Int {
        return savedStateHandle.get<Int>(NavScreen.Detail.userIdArgument)
            ?: throw IllegalArgumentException("You have to provide userId as parameter with type Int when navigating to details")
    }

}
