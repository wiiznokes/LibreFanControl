package ui.screen.itemsList.controlList

import State
import androidx.compose.runtime.snapshots.SnapshotStateList
import model.hardware.Control
import model.item.behavior.Behavior
import model.item.control.IControl
import ui.utils.Resources
import utils.Id
import utils.Name
import utils.Name.Companion.checkNameTaken

class ControlVM(
    val iControls: SnapshotStateList<IControl> = State.iControls,
    val iBehaviors: SnapshotStateList<Behavior> = State.iBehaviors,
    val hControls: SnapshotStateList<Control> = State.hControls
) {
    fun remove(index: Int) {
        iControls.removeAt(index)
    }

    fun setControl(index: Int, controlId: Long?) {
        iControls[index] = iControls[index].copy(
            controlId = controlId
        )
    }

    fun setBehavior(index: Int, behaviorId: Long?) {
        iControls[index] = iControls[index].copy(
            behaviorId = behaviorId
        )
    }

    fun onSwitchClick(checked: Boolean, index: Int) {
        iControls[index] = iControls[index].copy(
            isAuto = !checked
        )

    }


    fun setName(name: String, index: Int) {
        checkNameTaken(
            names = iControls.map { item ->
                item.name
            },
            name = name,
            index = index
        )
        iControls[index] = iControls[index].copy(
            name = name
        )
    }

    fun addControl() {
        val name = Name.getAvailableName(
            names = iControls.map { item ->
                item.name
            },
            prefix = Resources.getString("default/control_name")
        )

        iControls.add(
            IControl(
                name = name,
                id = Id.getAvailableId(
                    ids = iControls.map { item ->
                        item.id
                    }
                )
            )
        )
    }
}