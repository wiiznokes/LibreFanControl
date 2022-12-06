package ui.screen.body

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
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

    Box {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    color = MaterialTheme.colorScheme.background
                ),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
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
        }

        Button(
            modifier = Modifier
                .align(Alignment.BottomEnd),
            onClick = {

            }
        ) {
            Text(
                text = "Add"
            )
        }
    }
}