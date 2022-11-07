package com.hivian.lydia_test.ui.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.hivian.lydia_test.core.models.domain.RandomUserDomain
import com.hivian.lydia_test.presentation.home.HomeViewModel

@Preview
@Composable
fun Home(
    viewModel: HomeViewModel = viewModel()
) {
    val randomUsers = viewModel.items
    viewModel.initialize()

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        InitUserList(randomUsers) {
            viewModel.openRandomUserDetail(it)
        }
    }
}

@Composable
fun InitUserList(randomUsers: List<RandomUserDomain>, onItemClick : (RandomUserDomain) -> Unit) {
    LazyColumn(
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(randomUsers) { user ->
            UserListItem(user = user, onItemClick = onItemClick)
        }
    }
}

@Composable
fun UserListItem(user: RandomUserDomain, onItemClick : (RandomUserDomain) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onItemClick(user) },
        elevation = CardDefaults.elevatedCardElevation(),
        shape = RoundedCornerShape(8.dp)
    ) {
        Row {
            UserImage(imageUrl = user.picture)
            Column(
                Modifier
                    .padding(8.dp)
                    .fillMaxWidth()
                    .align(Alignment.CenterVertically),
            ) {
                Text(text = user.fullName, style = MaterialTheme.typography.headlineSmall)
                Text(text = user.email, style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}

@Composable
private fun UserImage(imageUrl : String) {
    Image(
        painter = rememberAsyncImagePainter(imageUrl),
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .size(66.dp)
            .clip(RoundedCornerShape(
                topStart = CornerSize(8.dp),
                topEnd = CornerSize(0.dp),
                bottomStart = CornerSize(8.dp),
                bottomEnd = CornerSize(0.dp))
            )
    )
}
