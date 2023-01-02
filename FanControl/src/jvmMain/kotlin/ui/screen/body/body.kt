package ui.screen.body

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material.IconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ui.component.managerText
import ui.screen.itemsList.behaviorList.behaviorBodyList
import ui.screen.itemsList.controlList.controlBody
import ui.screen.itemsList.controlList.controlList
import ui.screen.itemsList.sensor.body.fanList.fanBodyList
import ui.screen.itemsList.sensor.body.tempList.tempBodyList
import ui.utils.Resources

@Composable
fun body() {

    val viewModel = BodyVM()

    // body + addItem button
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        scrollableBox {
            Row(
                modifier = Modifier,
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.Top
            ) {
                itemsList(
                    title = Resources.getString("title/control")
                ) {
                    controlList({ it.visible }) { index, control ->
                        controlBody(
                            control = control,
                            index = index
                        )
                    }
                }
                itemsList(
                    title = Resources.getString("title/behavior")
                ) {
                    behaviorBodyList()
                }
                itemsList(
                    title = Resources.getString("title/fan")
                ) {
                    fanBodyList()
                }
                itemsList(
                    title = Resources.getString("title/temp")
                ) {
                    tempBodyList()
                }
            }
        }

        val addItemExpanded = viewModel.addItemExpanded.collectAsState()

        // add button
        if (!addItemExpanded.value) {

            IconButton(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(20.dp),
                onClick = {
                    viewModel.expandAddItem()
                }
            ) {
                Icon(
                    painter = Resources.getIcon("add"),
                    contentDescription = Resources.getString("ct/open_add_item"),
                    tint = MaterialTheme.colorScheme.onBackground
                )
            }
        }
    }
}

@Composable
private fun itemsList(
    title: String,
    content: LazyListScope.() -> Unit
) {
    LazyColumn(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            managerText(
                text = title,
                modifier = Modifier
                    .padding(10.dp),
                style = MaterialTheme.typography.titleLarge
            )
            Spacer(
                modifier = Modifier
                    .height(20.dp)
            )
        }
        content()
    }
}


@Composable
private fun BoxScope.scrollableBox(
    content: @Composable () -> Unit
) {
    val stateHorizontal = rememberScrollState(0)

    val scrollBarShouldShow =
        stateHorizontal.value != stateHorizontal.maxValue || stateHorizontal.value != 0

    val modifier = if (scrollBarShouldShow)
        Modifier.padding(bottom = 20.dp)
    else
        Modifier

    Box(
        modifier = modifier
            .horizontalScroll(stateHorizontal)

    ) {
        content()
    }

    if (scrollBarShouldShow) {
        HorizontalScrollbar(
            modifier = Modifier.align(Alignment.BottomStart)
                .height(20.dp)
                .background(MaterialTheme.colorScheme.secondary),
            adapter = rememberScrollbarAdapter(stateHorizontal)
        )
    }
}