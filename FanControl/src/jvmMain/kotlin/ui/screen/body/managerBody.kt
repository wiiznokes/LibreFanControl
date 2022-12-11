package ui.screen.body

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import ui.component.itemsList
import ui.screen.body.behaviorList.behaviorList
import ui.screen.body.controlList.controlList
import ui.screen.body.fanList.fanList
import ui.screen.body.tempList.tempList
import ui.utils.Resources

@Composable
fun body(
    editModeActivated: MutableState<Boolean>
) {

    val viewModel = BodyViewModel()


    Row(
        modifier = Modifier
        //.fillMaxSize()
        ,
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        itemsList(
            title = Resources.getString("title_control")
        ) {
            controlList(editModeActivated.value)
        }
        itemsList(
            title = Resources.getString("title_behavior")
        ) {
            behaviorList(editModeActivated.value)
        }
        itemsList(
            title = Resources.getString("title_fan")
        ) {
            fanList(editModeActivated.value)
        }
        itemsList(
            title = Resources.getString("title_temp")
        ) {
            tempList(editModeActivated.value)
        }
    }
}