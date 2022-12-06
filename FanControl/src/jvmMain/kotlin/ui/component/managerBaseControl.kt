package ui.component

import Source
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Switch
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.graphics.painter.Painter
import model.item.BehaviorItem


@Composable
fun baseControl(
    onEditClick: () -> Unit,
    source: Source,
    iconPainter: Painter,
    iconContentDescription: String,
    name: String,
    label: String? = null,
    onNameChange: ((String) -> Unit)? = null,
    editModeActivated: Boolean? = null,

    behaviorName: String,
    isActive: Boolean = false,
    onSwitchClick: ((Boolean) -> Unit)? = null,
    value: String = "50 %",
    fanValue: String = "2000 RPM",
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
                    onSwitchClick?.invoke(it)
                }
            )

            listChoice(
                sensorName = behaviorName,
                behaviorItemList = behaviorItemList,
                onItemClick = onItemClick
            )


        }
        Row {
            managerTextField(value)
            managerTextField(fanValue)
        }
    }
}