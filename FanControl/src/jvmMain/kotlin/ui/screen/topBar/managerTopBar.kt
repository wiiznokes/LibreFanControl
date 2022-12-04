package ui.screen.topBar

import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable

@Composable
fun topBar() {

    val viewModel = TopBarViewModel()

    CenterAlignedTopAppBar(
        title = {
            Text(
                text = "FanControl",
                style = MaterialTheme.typography.titleMedium
            )
        },
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = MaterialTheme.colorScheme.onPrimary,
            navigationIconContentColor = MaterialTheme.colorScheme.onPrimary
        ),
        actions = {
            Button(
                onClick = {
                    viewModel.edit()
                }
            ) {
                Text(
                    text = "Edit"
                )
            }
        }
    )
}