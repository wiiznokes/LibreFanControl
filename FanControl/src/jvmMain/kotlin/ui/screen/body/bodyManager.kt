package ui.screen.body

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ui.MainViewModel
import ui.UiStates
import ui.component.behavior.fan
import ui.component.utils.managerTextHomeScreen


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun listItemManager(
    title: String,
    viewModel: MainViewModel,
    uiStates: State<UiStates>
) {
    LazyColumn {
        stickyHeader {
            managerTextHomeScreen(
                text = title
            )
            Spacer(
                modifier = Modifier
                    .height(20.dp)
            )
        }
        when(title) {

        }
        itemsIndexed(uiStates.value.fanList) { index, item ->
            Spacer(
                modifier = Modifier
                    .height(5.dp)
            )
            fan(
                fan = item,
                viewModel = viewModel,
                index = index
            )
        }
    }
}
