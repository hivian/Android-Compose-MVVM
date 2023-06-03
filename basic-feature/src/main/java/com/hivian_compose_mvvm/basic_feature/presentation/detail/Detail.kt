package com.hivian_compose_mvvm.basic_feature.presentation.detail

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.hivian_compose_mvvm.basic_feature.R

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun DetailScreen(viewModel: DetailViewModel = viewModel()) {
    viewModel.initialize()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = viewModel.name.value) },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            viewModel.navigateBack()
                        }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "backIcon"
                        )
                    }
                },
            )
        }
    ) { contentPadding ->
        Column(
            Modifier
                .padding(contentPadding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ImageDetail(
                imageUrlPath = viewModel.picture.value
            )
            UserInfo(
                email = viewModel.email.value,
                phone = viewModel.phone.value,
                cell = viewModel.cell.value
            )
        }
    }
}

@Composable
fun ImageDetail(imageUrlPath : String) {
    val painter = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .data(imageUrlPath)
            .crossfade(true)
            .build()
    )

    Box(
        modifier = Modifier
            .fillMaxWidth(0.5f)
            .aspectRatio(1f),
        contentAlignment = Alignment.Center,
    ) {
        if (painter.state is AsyncImagePainter.State.Loading) {
            CircularProgressIndicator()
        }

        Image(
            painter = painter,
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
                .clip(CircleShape)
                .border(1.dp, MaterialTheme.colorScheme.primary, CircleShape)
        )
    }
}

@Composable
fun UserInfo(email: String, phone: String, cell: String) {
    Card(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        elevation = CardDefaults.elevatedCardElevation(),
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(
            Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            UserInfoItem(drawableStart = R.drawable.ic_email_24dp, text = email)
            UserInfoItem(drawableStart = R.drawable.ic_local_phone_24dp, text = phone)
            UserInfoItem(drawableStart = R.drawable.ic_cell_24dp, text = cell)
        }
    }
}

@Composable
fun UserInfoItem(@DrawableRes drawableStart: Int, text: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Image(painter = painterResource(id = drawableStart), contentDescription = null)
        Text(
            modifier = Modifier.padding(start = 8.dp),
            text = text,
            style = MaterialTheme.typography.titleMedium,
        )
    }
}
