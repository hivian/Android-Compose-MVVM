package com.hivian.lydia_test.ui.composables

import android.annotation.SuppressLint
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.hivian.lydia_test.presentation.detail.DetailViewModel
import com.hivian.lydia_test.presentation.detail.DetailViewModelFactory

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun DetailScreen(userId: Int = 0, viewModel: DetailViewModel = viewModel(
    factory = DetailViewModelFactory(userId)
)) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Detail Screen $userId") },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            viewModel.navigateBack()
                        }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "backIcon",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = Color.White,
                )
            )
        }
    ) {

    }
}