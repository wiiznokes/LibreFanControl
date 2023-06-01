package ui.screen.itemsList.controlList

import FState
import androidx.compose.runtime.snapshots.SnapshotStateList
import model.hardware.HControl
import model.item.BaseI
import model.item.BaseI.Companion.checkNameValid
import model.item.BaseI.Companion.getAvailableId
import model.item.BaseI.Companion.getAvailableName
import model.item.BaseIBehavior
import model.item.IControl
import utils.Resources

class ControlVM(
    val iControls: SnapshotStateList<IControl> = FState.iControls,
    val iBehaviors: SnapshotStateList<BaseIBehavior> = FState.iBehaviors,
    val hControls: SnapshotStateList<HControl> = FState.hControls,
) {
    fun remove(index: Int) {
        iControls.removeAt(index)
    }

    fun setControl(index: Int, hControlId: String?) {
        iControls[index].hControlId.value = hControlId
    }

    fun setBehavior(index: Int, iBehaviorId: String?) {
        iControls[index].iBehaviorId.value = iBehaviorId
    }

    fun onSwitchClick(checked: Boolean, index: Int) {
        iControls[index].isAuto.value = !checked
    }


    fun setName(name: String, index: Int) {
        checkNameValid(
            names = iControls.map { item ->
                item.name.value
            },
            name = name,
            index = index
        )
        iControls[index].name.value = name
    }

    fun addControl() {
        iControls.add(
            IControl(
                name = getAvailableName(
                    list = iControls.map { item ->
                        item.name.value
                    },
                    prefix = Resources.getString("default/control_name")
                ),
                id = getAvailableId(
                    list = iControls.map { item ->
                        item.id
                    },
                    prefix = BaseI.IControlPrefix
                )
            )
        )
    }
}