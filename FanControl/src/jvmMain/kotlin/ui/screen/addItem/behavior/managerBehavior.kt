package ui.screen.addItem.behavior

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable


val viewModel = AddBehaviorViewModel()
@Composable
fun managerAddBehavior() {
    LazyColumn {
        item {
            managerAddFlat(
                onEditClick = { viewModel.addFlat() }
            )
        }
        item {
            managerAddLinear(
                onEditClick = { viewModel.addLinear() }
            )
        }
    }
}