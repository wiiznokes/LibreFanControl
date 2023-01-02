package ui.screen.addItem.behavior

import androidx.compose.foundation.lazy.LazyListScope


val viewModel = AddBehaviorViewModel()


fun LazyListScope.managerAddBehavior() {

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