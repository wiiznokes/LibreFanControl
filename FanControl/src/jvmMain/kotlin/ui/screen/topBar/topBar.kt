package ui.screen.topBar

import androidx.compose.foundation.layout.*
import androidx.compose.material.IconButton
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ui.component.managerText
import ui.screen.topBar.configuration.configuration
import ui.utils.Resources


private val viewModel = TopBarVM()
private val topBarHeight = 50.dp

@Composable
fun topBarBody(
    onNavigationIconClick: () -> Unit
) {
    MediumTopAppBar(
        modifier = Modifier
            .height(topBarHeight),
        title = {
            Row(
                modifier = Modifier
                    .fillMaxHeight(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = Resources.getIcon("topBar/toys_fan"),
                    contentDescription = Resources.getString("title/app_name"),
                    tint = MaterialTheme.colorScheme.onPrimaryContainer
                )
                Spacer(Modifier.width(10.dp))

                managerText(
                    modifier = Modifier,
                    text = Resources.getString("title/app_name"),
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
        },
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        navigationIcon = {
            IconButton(
                onClick = {
                    onNavigationIconClick()
                }
            ) {
                Icon(
                    painter = Resources.getIcon("topBar/menu"),
                    contentDescription = Resources.getString("ct/open_drawer"),
                    tint = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
        },
        actions = {
            Row(
                modifier = Modifier
                    .fillMaxHeight(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                configuration()

                Divider(
                    modifier = Modifier
                        .fillMaxHeight(0.8f)
                        .padding(horizontal = 10.dp)
                        .width(2.dp),
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )

                IconButton(
                    onClick = {
                        viewModel.edit()
                    }
                ) {
                    Icon(
                        painter = Resources.getIcon("topBar/edit_square"),
                        contentDescription = Resources.getString("ct/edit"),
                        modifier = Modifier
                            .padding(bottom = 5.dp, top = 1.dp),
                        tint = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
            }
        }
    )
}


@Composable
fun topBarAddItem() {
    CenterAlignedTopAppBar(
        modifier = Modifier
            .height(topBarHeight),
        title = {
            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                managerText(
                    modifier = Modifier
                        .align(Alignment.Center),
                    text = Resources.getString("title/add_item"),
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.onSecondaryContainer
                )
            }
        },
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer
        ),
        navigationIcon = {
            IconButton(
                onClick = { viewModel.closeAddItem() }
            ) {
                Icon(
                    painter = Resources.getIcon("arrow/arrow_forward"),
                    contentDescription = Resources.getString("ct/close_add_item"),
                    tint = MaterialTheme.colorScheme.onSecondaryContainer
                )
            }
        }
    )
}