package ui.screen.topBar

import androidx.compose.foundation.layout.*
import androidx.compose.material.IconButton
import androidx.compose.material3.*
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.WindowState
import ui.component.managerText
import ui.screen.topBar.configuration.managerConfiguration
import ui.utils.Resources


private val viewModel = TopBarViewModel()

@Composable
fun mainTopBar(
    onNavigationIconClick: () -> Unit,
    windowState: WindowState
) {



    MediumTopAppBar(
        modifier = Modifier
            .height(50.dp),
        title = {
            Row(
                modifier = Modifier
                    .fillMaxHeight(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = Resources.getIcon("toys_fan"),
                    contentDescription = Resources.getString("title/app_name")
                )
                Spacer(
                    modifier = Modifier
                        .width(10.dp)
                )
                managerText(
                    modifier = Modifier,
                    text = Resources.getString("title/app_name"),
                    style = MaterialTheme.typography.titleMedium
                )
            }
        },
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.tertiary,
            titleContentColor = MaterialTheme.colorScheme.onTertiary,
            navigationIconContentColor = MaterialTheme.colorScheme.onTertiary
        ),
        navigationIcon = {
            IconButton(
                onClick = {
                    onNavigationIconClick()
                }
            ) {
                Icon(
                    painter = Resources.getIcon("menu"),
                    contentDescription = Resources.getString("ct/open_drawer")
                )
            }
        },
        actions = {

            Row (
                modifier = Modifier
                    .fillMaxHeight(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ){
                managerConfiguration(
                    windowState = windowState
                )

                Divider(
                    modifier = Modifier
                        .width(2.dp)
                        .padding(
                            horizontal = 10.dp
                        )
                )

                IconButton(
                    onClick = {
                        viewModel.edit()
                    }
                ) {
                    Icon(
                        painter = Resources.getIcon("edit_square"),
                        contentDescription = Resources.getString("ct/edit")
                    )
                }
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
                    text = Resources.getString("title/add_item"),
                    style = MaterialTheme.typography.titleSmall
                )
            }
        },
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.tertiary,
            titleContentColor = MaterialTheme.colorScheme.onTertiary,
            navigationIconContentColor = MaterialTheme.colorScheme.onTertiary
        ),
        navigationIcon = {
            IconButton(
                onClick = {
                    viewModel.unexpandAddItem()
                }
            ) {
                Icon(
                    painter = Resources.getIcon("arrow_forward"),
                    contentDescription = Resources.getString("ct/close_add_item")
                )
            }
        }
    )
}