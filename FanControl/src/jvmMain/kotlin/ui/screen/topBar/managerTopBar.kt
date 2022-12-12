package ui.screen.topBar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import ui.component.managerText

@Composable
fun mainTopBar() {

    val viewModel = TopBarViewModel()

    CenterAlignedTopAppBar(
        modifier = Modifier
            .height(50.dp),
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


@Composable
fun addItemTopBar(
    modifier: Modifier
) {
    Box(
        modifier = modifier
            .height(50.dp)
            .width(200.dp)
    ){
        Button(
            modifier = Modifier
                .align(Alignment.CenterStart),
            onClick = {
            }
        ) {
            Text(
                text = "Return"
            )
        }

        managerText(
            modifier = Modifier
                .align(Alignment.Center)
                .background(Color.Black),
            value = "addItem"
        )
    }
}