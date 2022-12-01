package ui

import androidx.compose.runtime.snapshots.SnapshotStateList
import model.Control
import model.Fan
import model.Temp

data class UiStates(
    val fans: SnapshotStateList<Fan>,
    val temps: SnapshotStateList<Temp>,
    val controls: SnapshotStateList<Control>
)
