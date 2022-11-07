package com.hivian.lydia_test.ui.composables

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

@Preview
@Composable
fun Detail() {
    Text("Detail Screen", style = MaterialTheme.typography.headlineSmall)
}