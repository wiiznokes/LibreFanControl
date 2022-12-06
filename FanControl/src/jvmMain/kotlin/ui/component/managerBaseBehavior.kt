package ui.component

import Source
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Slider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
import ui.utils.Resources


@Composable
fun baseBehavior(
    iconPainter: Painter,
    iconContentDescription: String,
    name: String,
    onEditClick: () -> Unit,
    source: Source,
    label: String,
    onNameChange: (String) -> Unit,
    editModeActivated: Boolean,


    value: Int = 50,
    onMoreButtonClick: (Int) -> Unit,
    onLessButtonClick: (Int) -> Unit,
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
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            managerText("Fan speed")

            Box {
                Row {


                    IconButton(
                        onClick = {
                            onLessButtonClick(value)
                        }
                    ) {
                        Icon(
                            painter = Resources.getIcon("remove"),
                            contentDescription = Resources.getString("editContentDescriptionRemove")
                        )
                    }
                    IconButton(
                        onClick = {
                            onMoreButtonClick(value)
                        }
                    ) {
                        Icon(
                            painter = Resources.getIcon("add"),
                            contentDescription = Resources.getString("editContentDescriptionAdd")
                        )
                    }
                }
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth(),

            verticalAlignment = Alignment.CenterVertically,
        ) {

            Slider(
                value = value.toFloat() / 100,
                steps = 100,
                onValueChange = {
                    onSliderValueChange(
                        (it * 100).toInt()
                    )
                },
                modifier = Modifier
                    .fillMaxWidth(0.75f)
            )



            Spacer(
                modifier = Modifier
                    .width(10.dp)
            )
            managerText(
                value = "$value %"
            )
        }
    }
}