package ui.screen.topBar

import androidx.compose.foundation.layout.*
import androidx.compose.material.IconButton
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ui.component.managerText
import ui.utils.Resources


private val viewModel = TopBarViewModel()

@Composable
fun mainTopBar(
    onNavigationIconClick: () -> Unit,
) {

    CenterAlignedTopAppBar(
        modifier = Modifier
            .height(50.dp),
        title = {
            Row(
                modifier = Modifier
                    .fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Icon(
                    painter = Resources.getIcon("toys_fan"),
                    contentDescription = Resources.getString("app_name")
                )
                Spacer(
                    modifier = Modifier
                        .width(10.dp)
                )
                managerText(
                    modifier = Modifier,
                    text = Resources.getString("app_name"),
                    style = MaterialTheme.typography.titleMedium
                )
            }
        },
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = MaterialTheme.colorScheme.onPrimary,
            navigationIconContentColor = MaterialTheme.colorScheme.onPrimary
        ),
        navigationIcon = {
            IconButton(
                onClick = {
                    onNavigationIconClick()
                }
            ) {
                Icon(
                    painter = Resources.getIcon("menu"),
                    contentDescription = Resources.getString("open_drawer_button_content_description")
                )
            }
        },
        actions = {
            IconButton(
                onClick = {
                    viewModel.edit()
                }
            ) {
                Icon(
                    painter = Resources.getIcon("edit_square"),
                    contentDescription = Resources.getString("edit_button_content_description")
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
                managerText(
                    modifier = Modifier
                        .align(Alignment.Center),
                    text = Resources.getString("add_item_app_bar_title"),
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
            IconButton(
                onClick = {
                    viewModel.unexpandAddItem()
                }
            ) {
                Icon(
                    painter = Resources.getIcon("arrow_forward"),
                    contentDescription = Resources.getString("add_item_back_button_content_description")
                )
            }
        }
    )
}