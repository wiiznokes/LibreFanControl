package ui.screen.topBar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
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


private val viewModel = TopBarViewModel()

@Composable
fun mainTopBar() {

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
    CenterAlignedTopAppBar(
        modifier = modifier
            .height(50.dp),
        title = {
            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                Text(
                    modifier = Modifier
                        .align(Alignment.Center),
                    text = "Add Item",
                    style = MaterialTheme.typography.titleSmall
                )
            }
        },
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = MaterialTheme.colorScheme.onPrimary,
            navigationIconContentColor = MaterialTheme.colorScheme.onPrimary
        ),
        navigationIcon = {
            Button(
                onClick = {
                    viewModel.unexpandAddItem()
                }
            ) {
                Text(
                    text = "return"
                )
            }
        }
    )
}