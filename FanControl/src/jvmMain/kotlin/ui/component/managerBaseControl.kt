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
import model.ItemType
import model.item.ControlItem
import model.item.behavior.BehaviorItem
import ui.utils.Resources


@Composable
fun baseControlBody(
    onEditClick: () -> Unit,
    onNameChange: (String) -> Unit,
    editModeActivated: Boolean,

    onSwitchClick: (Boolean) -> Unit,
    value: String,
    fanValue: String,
    behaviorItemList: SnapshotStateList<BehaviorItem>,
    onBehaviorChange: (Long?) -> Unit,
    control: ControlItem
) {
    baseItemBody(
        iconPainter = Resources.getIcon("alternate_email"),
        iconContentDescription = Resources.getString("ct/control"),
        onNameChange = onNameChange,
        editModeActivated = editModeActivated,
        onEditClick = onEditClick,
        item = control
    ) {
        baseControl(
            isAuto = control.isAuto,
            switchEnabled = control.logicHasVerify,
            onSwitchClick = onSwitchClick,
            value = value,
            fanValue = fanValue,

            ) {
            managerListChoice(
                text = behaviorItemList.firstOrNull {
                    it.itemId == control.behaviorId
                }?.name,
                onItemClick = onBehaviorChange,
                ids = behaviorItemList.map { it.itemId },
                names = behaviorItemList.map { it.name },
                enabled = control.logicHasVerify
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
    fanValue: String
) {
    baseItemAddItem(
        iconPainter = Resources.getIcon("alternate_email"),
        iconContentDescription = Resources.getString("ct/control"),
        name = name,
        onEditClick = onEditClick,
        type = ItemType.ControlType.I_C_FAN
    ) {
        baseControl(
            isAuto = true,
            switchEnabled = false,
            onSwitchClick = {},
            value = value,
            fanValue = fanValue
        ) {
            managerAddItemListChoice(
                name = behaviorName
            )
        }
    }

}


@Composable
private fun baseControl(
    isAuto: Boolean,
    switchEnabled: Boolean,
    onSwitchClick: (Boolean) -> Unit,
    value: String,
    fanValue: String,
    contentListChoice: @Composable () -> Unit
) {

    Row {

        Switch(
            enabled = switchEnabled,
            checked = !isAuto,
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