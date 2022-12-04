package ui.screen.addItem.controlList

import Source
import androidx.compose.runtime.Composable
import model.hardware.control.Control
import ui.component.baseControl
import ui.utils.Resources

@Composable
fun addControl(
    control: Control,
    index: Int
) {
    val viewModel = AddControlViewModel()

    baseControl(
        iconPainter = Resources.getIcon("alternate_email"),
        iconContentDescription = "",
        name = control.name,
        onEditClick = { viewModel.add(index) },
        source = Source.ADD,
        behaviorName = ""
    )
}


