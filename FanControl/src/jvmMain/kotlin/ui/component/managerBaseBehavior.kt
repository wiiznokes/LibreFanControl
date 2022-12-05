package ui.component

import Source
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Slider
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import kotlinx.coroutines.flow.StateFlow
import ui.utils.Resources


@Composable
fun baseBehavior(
    iconPainter: Painter,
    iconContentDescription: String,
    name: String,
    onEditClick: () -> Unit,
    source: Source,
    label: String? = null,
    onNameChange: ((String) -> Unit)? = null,
    editModeActivated: Boolean? = null,


    value: Int = 50,
    onMoreButtonClick: (() -> Unit)? = null,
    onLessButtonClick: (() -> Unit)? = null,
    onSliderValueChange: ((Int) -> Unit)? = null
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
            managerTextField("Fan speed")

            IconButton(
                onClick = {
                    onMoreButtonClick?.invoke()
                }
            ) {
                Icon(
                    painter = Resources.getIcon("add"),
                    contentDescription = Resources.getString("editContentDescriptionAdd")
                )
            }
            IconButton(
                onClick = {
                    onLessButtonClick?.invoke()
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
                onSliderValueChange?.invoke(
                    (it * 100).toInt()
                )
            }
        )
    }
}