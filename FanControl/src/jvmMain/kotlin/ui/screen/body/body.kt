package ui.screen.body

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import ui.MainViewModel
import ui.UiStates
import ui.utils.Resources

@Composable
fun body(viewModel: MainViewModel, uiStates: State<UiStates>) {
    Row(
        modifier = Modifier
            .fillMaxSize()
            .background(
                color = MaterialTheme.colorScheme.background
            ),
        horizontalArrangement = Arrangement.SpaceAround
    ) {

        listOf(
            Resources.getString("title_fan"),
            Resources.getString("title_temp"),
            Resources.getString("title_control"),
            Resources.getString("title_behavior")
        ).forEach {
            managerBodyListItem(
                title = it,
                viewModel = viewModel,
                uiStates = uiStates
            )
        }
    }
}