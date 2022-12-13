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
fun baseControlBody(
    name: String,
    onEditClick: () -> Unit,
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
    baseItemBody(
        iconPainter = Resources.getIcon("alternate_email"),
        iconContentDescription = Resources.getString("control_icon_content_description"),
        name = name,
        onNameChange = onNameChange,
        editModeActivated = editModeActivated,
        onEditClick = onEditClick
    ) {
        baseControl(
            isActive = isActive,
            switchEnabled = true,
            onSwitchClick = onSwitchClick,
            value = value,
            fanValue = fanValue,

            ) {
            listChoice(
                sensorName = behaviorName,
                behaviorItemList = behaviorItemList,
                onItemClick = onItemClick
            )
        }
    }
}

@Composable
fun baseControlAddItem(
    name: String,
    onEditClick: () -> Unit,
    behaviorName: String,
    value: String,
    fanValue: String,
) {
    baseItemAddItem(
        iconPainter = Resources.getIcon("alternate_email"),
        iconContentDescription = Resources.getString("control_icon_content_description"),
        name = name,
        onEditClick = onEditClick
    ) {
        baseControl(
            isActive = false,
            switchEnabled = false,
            onSwitchClick = {},
            value = value,
            fanValue = fanValue
        ) {
            listChoice(
                name = behaviorName
            )
        }
    }

}


@Composable
private fun baseControl(
    isActive: Boolean,
    switchEnabled: Boolean,
    onSwitchClick: (Boolean) -> Unit,
    value: String,
    fanValue: String,
    contentListChoice: @Composable () -> Unit
) {

    Row {

        Switch(
            enabled = switchEnabled,
            checked = isActive,
            onCheckedChange = {
                onSwitchClick(it)
            }
        )
        Spacer(
            modifier = Modifier
                .width(10.dp)
        )

        contentListChoice()


    }
    Row(
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        managerText(value)
        managerText(fanValue)
    }

}