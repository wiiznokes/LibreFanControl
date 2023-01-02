package ui.screen.body

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material.IconButton
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ui.component.managerText
import ui.screen.itemsList.behaviorList.behaviorBodyList
import ui.screen.itemsList.controlList.controlBody
import ui.screen.itemsList.controlList.controlList
import ui.screen.itemsList.sensor.body.fanList.fanBodyList
import ui.screen.itemsList.sensor.body.tempList.tempBodyList
import ui.theme.floatingActionButtonPadding
import ui.theme.scrollBarHeight
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
        val scrollBarShouldShow = mutableStateOf(false)
        scrollableBox(
            scrollBarShouldShow = scrollBarShouldShow
        ) {
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

            FloatingActionButton(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(
                        end = floatingActionButtonPadding,
                        bottom = if (scrollBarShouldShow.value) scrollBarHeight + floatingActionButtonPadding
                        else floatingActionButtonPadding),
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                onClick = { viewModel.expandAddItem() }
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
            Spacer(Modifier.height(20.dp))
        }
        content()
        item {
            Spacer(Modifier.height(80.dp))
        }
    }
}


@Composable
private fun BoxScope.scrollableBox(
    scrollBarShouldShow: MutableState<Boolean>,
    content: @Composable () -> Unit
) {
    val stateHorizontal = rememberScrollState(0)

    scrollBarShouldShow.value = stateHorizontal.value != stateHorizontal.maxValue || stateHorizontal.value != 0

    val modifier = if (scrollBarShouldShow.value)
        Modifier.padding(bottom = scrollBarHeight)
    else
        Modifier

    Box(
        modifier = modifier
            .horizontalScroll(stateHorizontal)

    ) {
        content()
    }

    if (scrollBarShouldShow.value) {
        HorizontalScrollbar(
            modifier = Modifier.align(Alignment.BottomStart)
                .height(scrollBarHeight)
                .background(MaterialTheme.colorScheme.secondary),
            adapter = rememberScrollbarAdapter(stateHorizontal)
        )
    }
}