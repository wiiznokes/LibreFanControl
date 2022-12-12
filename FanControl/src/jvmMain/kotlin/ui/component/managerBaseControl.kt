package ui.component


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Switch
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import model.item.BehaviorItem
import ui.utils.Resources


@Composable
fun baseControl(
    onEditClick: () -> Unit,
    name: String,
    onNameChange: (String) -> Unit,
    editModeActivated: Boolean,

    behaviorName: String,
    isActive: Boolean,
    onSwitchClick: (Boolean) -> Unit,
    value: String,
    fanValue: String,
    behaviorItemList: SnapshotStateList<BehaviorItem>,
    onItemClick: (BehaviorItem?) -> Unit
) {
    baseItem(
        iconPainter = Resources.getIcon("alternate_email"),
        iconContentDescription = Resources.getString("control_icon_content_description"),
        name = name,
        onNameChange = onNameChange,
        editModeActivated = editModeActivated,
        onEditClick = onEditClick
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
        Row(
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            managerText(value)
            managerText(fanValue)
        }
    }
}