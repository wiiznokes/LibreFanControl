package ui.screen.itemsList.controlList

import State
import androidx.compose.runtime.snapshots.SnapshotStateList
import model.hardware.HControl
import model.item.BaseI
import model.item.BaseI.Companion.checkNameTaken
import model.item.BaseI.Companion.getAvailableString
import model.item.BaseIBehavior
import model.item.IControl
import ui.utils.Resources

class ControlVM(
    val iControls: SnapshotStateList<IControl> = State.iControls,
    val iBehaviors: SnapshotStateList<BaseIBehavior> = State.iBehaviors,
    val hControls: SnapshotStateList<HControl> = State.hControls,
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
        checkNameTaken(
            names = iControls.map { item ->
                item.name.value
            },
            name = name,
            index = index
        )
        iControls[index].name.value = name
    }

    fun addControl() {
        val name = getAvailableString(
            list = iControls.map { item ->
                item.name.value
            },
            prefix = Resources.getString("default/control_name")
        )

        iControls.add(
            IControl(
                name = name,
                id = getAvailableString(
                    list = iControls.map { item ->
                        item.id
                    },
                    prefix = BaseI.IControlPrefix
                )
            )
        )
    }
}