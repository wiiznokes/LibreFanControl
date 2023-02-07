package ui.screen.itemsList.sensor.body.fanList

import State
import androidx.compose.runtime.snapshots.SnapshotStateList
import model.hardware.HFan
import model.item.BaseI.Companion.checkNameTaken
import model.item.IFan

class FanVM(
    val iFans: SnapshotStateList<IFan> = State.iFans,
    val hFans: SnapshotStateList<HFan> = State.hFans,
) {

    fun remove(index: Int) {
        iFans.removeAt(index)
    }

    fun setFan(index: Int, hFanId: String?) {
        iFans[index].hFanId.value = hFanId
    }

    fun setName(name: String, index: Int) {
        checkNameTaken(
            names = iFans.map { item ->
                item.name.value
            },
            name = name,
            index = index
        )
        iFans[index].name.value = name
    }
}