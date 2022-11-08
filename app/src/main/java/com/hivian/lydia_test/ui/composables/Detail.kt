package com.hivian.lydia_test.ui.composables

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.hivian.lydia_test.presentation.detail.DetailViewModel
import com.hivian.lydia_test.presentation.detail.DetailViewModelFactory

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun DetailScreen(userId: Int = 0, viewModel: DetailViewModel = viewModel(
    factory = DetailViewModelFactory(userId)
)) {
    val picture = viewModel.picture
    viewModel.initialize()

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
    ) { contentPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(contentPadding)

        ) {
            ConstraintLayout(
                modifier = Modifier.fillMaxWidth()
            ) {
                val (userImage) = createRefs()

                ImageDetail(
                    modifier = Modifier.constrainAs(userImage) {
                        top.linkTo(parent.top, margin = 16.dp)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    },
                    urlPath = picture.value
                )
            }
        }
    }
}

@Composable
fun ImageDetail(modifier: Modifier = Modifier, urlPath : String) {
    AsyncImage(
        model = urlPath,
        contentDescription = null,
        contentScale = ContentScale.FillWidth,
        modifier = modifier
            .fillMaxWidth(fraction = 0.5f)
            .clip(CircleShape)
    )
}