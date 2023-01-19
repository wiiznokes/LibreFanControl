package ui.screen.body

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.Transition
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.unit.dp
import ui.component.managerText
import ui.screen.itemsList.behaviorList.behaviorBodyList
import ui.screen.itemsList.controlList.controlBody
import ui.screen.itemsList.controlList.controlList
import ui.screen.itemsList.sensor.body.fanList.fanBodyList
import ui.screen.itemsList.sensor.body.tempList.tempBodyList
import ui.utils.Resources


private val floatingActionButtonPadding = 20.dp
private val scrollBarHeight = 20.dp

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun body(
    transition: Transition<Boolean>
) {

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

        val visibleState = remember { MutableTransitionState(!addItemExpanded.value) }
        visibleState.targetState = !addItemExpanded.value

        // add button
        transition.AnimatedVisibility(
            modifier = Modifier
                .align(Alignment.BottomEnd),
            visible = {
                !it
            },
            enter = slideInHorizontally(
                animationSpec = tween(delayMillis = 500),
                initialOffsetX = {
                    it
                }
            )
        ) {
            FloatingActionButton(
                modifier = Modifier
                    .scale(0.8f)
                    .padding(
                        end = floatingActionButtonPadding,
                        bottom = if (scrollBarShouldShow.value) scrollBarHeight + floatingActionButtonPadding
                        else floatingActionButtonPadding
                    ),
                containerColor = MaterialTheme.colorScheme.primary,
                onClick = { viewModel.expandAddItem() }
            ) {
                Icon(
                    painter = Resources.getIcon("sign/plus/add40"),
                    contentDescription = Resources.getString("ct/open_add_item"),
                    tint = MaterialTheme.colorScheme.onPrimary
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
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onBackground
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
                .background(MaterialTheme.colorScheme.tertiary),
            adapter = rememberScrollbarAdapter(stateHorizontal)
        )
    }
}