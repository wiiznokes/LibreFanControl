package ui.component

import Source
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Switch
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
import model.item.BehaviorItem


@Composable
fun baseControl(
    onEditClick: () -> Unit,
    source: Source,
    iconPainter: Painter,
    iconContentDescription: String,
    name: String,
    label: String,
    onNameChange: (String) -> Unit,
    editModeActivated: Boolean,

    behaviorName: String,
    isActive: Boolean,
    onSwitchClick: (Boolean) -> Unit,
    value: String,
    fanValue: String,
    behaviorItemList: SnapshotStateList<BehaviorItem>,
    onItemClick: (String) -> Unit
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

            Switch(
                checked = isActive,
                onCheckedChange = {
                    onSwitchClick(it)
                }
            )
            Spacer(
                modifier = Modifier
                    .width(10.dp)
            )

            listChoice(
                sensorName = behaviorName,
                behaviorItemList = behaviorItemList,
                onItemClick = onItemClick
            )


        }
        Row {
            managerText(value)
            managerText(fanValue)
        }
    }
}