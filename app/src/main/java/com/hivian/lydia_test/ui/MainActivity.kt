package com.hivian.lydia_test.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.hivian.lydia_test.core.services.navigation.INavigationService
import com.hivian.lydia_test.core.services.userinteraction.IUserInteractionService
import com.hivian.lydia_test.ui.themes.ComposetestTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject lateinit var navigationService: INavigationService

    @Inject lateinit var userInteractionService: IUserInteractionService

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val snackbarHostState = remember { SnackbarHostState() }

            ComposetestTheme {
                // A surface container using the 'background' color from the theme
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
                ) {
                    Surface(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(it),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        navigationService.InitNavController()
                        userInteractionService.InitSnackBarHostState(snackbarHostState)
                    }
                }

            }
        }
    }
}
