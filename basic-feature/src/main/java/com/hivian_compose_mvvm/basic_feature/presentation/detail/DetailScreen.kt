package com.hivian_compose_mvvm.basic_feature.presentation.detail

import android.content.res.Configuration
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.hivian.compose_mvvm.core.services.navigation.NavigationAction
import com.hivian_compose_mvvm.basic_feature.R
import org.koin.compose.koinInject
import org.koin.core.parameter.parametersOf

@Composable
fun DetailScreen(
    userId: Int,
    viewModel: DetailViewModel = koinInject(parameters = { parametersOf(userId) }),
    onNavigateBack: () -> Unit
) {
    viewModel.initialize()

    val navigationEventState = viewModel.navigationEvent.collectAsState()

    LaunchedEffect(navigationEventState.value) {
        when (navigationEventState.value) {
            is NavigationAction.Back -> onNavigateBack()
            else -> Unit
        }
    }

    DetailContent(
        DetailViewModelArg(
            picture = viewModel.picture,
            name = viewModel.name,
            email = viewModel.email,
            cell = viewModel.cell,
            phone = viewModel.phone,
            city = viewModel.city,
            country = viewModel.country,
            latitude = viewModel.latitude,
            longitude = viewModel.longitude,
            navigateBack = { viewModel.navigateBack() }
        )
    )
}

@Preview(name = "Light mode")
@Preview(name = "Dark mode", uiMode = Configuration.UI_MODE_NIGHT_YES)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailContent(
    @PreviewParameter(DetailViewModelArgProvider::class) viewModelArg: DetailViewModelArg
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = viewModelArg.name.value) },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            viewModelArg.navigateBack()
                        }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
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
                imageUrlPath = viewModelArg.picture.value
            )
            UserInfo(
                email = viewModelArg.email.value,
                phone = viewModelArg.phone.value,
                cell = viewModelArg.cell.value
            )
            GoogleMapAddress(
                latitude = viewModelArg.latitude.value,
                longitude = viewModelArg.longitude.value,
                city = viewModelArg.city.value,
                country = viewModelArg.country.value
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
            .padding(16.dp, 16.dp, 16.dp),
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

@Composable
fun GoogleMapAddress(
    latitude: Double, longitude: Double, city: String, country: String
) {
    val location = LatLng(latitude, longitude)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp)
            .padding(16.dp),
        shape = RoundedCornerShape(8.dp)
    ) {
        GoogleMap(
            cameraPositionState = CameraPositionState(
                position = CameraPosition.fromLatLngZoom(location, 8f)
            ),
            modifier = Modifier.fillMaxSize(),
            uiSettings = MapUiSettings(
                tiltGesturesEnabled = false,
                zoomControlsEnabled = false,
                zoomGesturesEnabled = false,
                scrollGesturesEnabled = false,
            )
        ) {
            Marker(
                state = MarkerState(position = location),
                title = city,
                snippet = country
            )
        }
    }

}
