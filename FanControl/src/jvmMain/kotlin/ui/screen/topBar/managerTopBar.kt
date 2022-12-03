package ui.screen.topBar.component

import androidx.compose.material.Text
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable

@Composable
fun topBar() {

    CenterAlignedTopAppBar(
        title = {
            Text(
                text = "FanControl",
                style = MaterialTheme.typography.titleMedium
            )
        }
    )
}