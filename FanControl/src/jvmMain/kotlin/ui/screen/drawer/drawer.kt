package ui.screen.drawer

import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.sp
import ui.component.managerText
import ui.utils.Resources


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun drawer(
    drawerState: DrawerState
) {
    managerText(
        text = Resources.getString("title/setting"),
        color = MaterialTheme.colorScheme.inverseOnSurface,
        style = MaterialTheme.typography.titleLarge
    )
}