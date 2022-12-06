package ui.component

import Source
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Slider
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import ui.utils.Resources


@Composable
fun baseBehavior(
    iconPainter: Painter,
    iconContentDescription: String,
    name: String,
    onEditClick: () -> Unit,
    source: Source,
    label: String,
    onNameChange: (String) -> Boolean,
    editModeActivated: Boolean,


    value: Int = 50,
    onMoreButtonClick: () -> Unit,
    onLessButtonClick: () -> Unit,
    onSliderValueChange: (Int) -> Unit
) {
    baseItem(
        iconPainter = iconPainter,
        iconContentDescription = iconContentDescription,
        name = name,
        label = label,
        onNameChange = onNameChange,
        editModeActivated = editModeActivated,
        onEditClick = onEditClick,
        source = source,
    ) {
        Row {
            managerText("Fan speed")

            IconButton(
                onClick = {
                    onMoreButtonClick()
                }
            ) {
                Icon(
                    painter = Resources.getIcon("add"),
                    contentDescription = Resources.getString("editContentDescriptionAdd")
                )
            }
            IconButton(
                onClick = {
                    onLessButtonClick()
                }
            ) {
                Icon(
                    painter = Resources.getIcon("add"),
                    contentDescription = Resources.getString("editContentDescriptionRemove")
                )
            }
        }

        Slider(
            value = value.toFloat() / 100,
            steps = 100,
            onValueChange = {
                onSliderValueChange(
                    (it * 100).toInt()
                )
            }
        )
    }
}