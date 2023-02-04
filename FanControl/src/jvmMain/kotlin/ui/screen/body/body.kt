package ui.screen.body

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material.Text
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ui.component.managerText
import ui.screen.itemsList.behaviorList.behaviorBodyList
import ui.screen.itemsList.controlList.controlBodyList
import ui.screen.itemsList.sensor.body.fanList.fanBodyList
import ui.screen.itemsList.sensor.body.tempList.tempBodyList
import ui.theme.LocalColors
import ui.utils.Resources


private val floatingActionButtonPadding = 20.dp
private val scrollBarHeight = 20.dp

@Composable
fun body(
    addItemAnimationState: MutableTransitionState<Boolean>
) {

    val viewModel = BodyVM()

    // body + addItem button
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(LocalColors.current.mainBackground)
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
                    controlBodyList()
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
        // visible only when add item transition has finish to avoid icon show to early
        visibleState.targetState = !addItemExpanded.value && addItemAnimationState.currentState == addItemExpanded.value

        // add button
        AnimatedVisibility(
            visibleState = visibleState,
            modifier = Modifier
                .align(Alignment.BottomEnd),
            enter = slideInHorizontally(
                animationSpec = tween(delayMillis = 500),
                initialOffsetX = {
                    it
                }
            ),
            exit = fadeOut()
        ) {
            FloatingActionButton(
                modifier = Modifier
                    .scale(0.75f)
                    .padding(
                        end = floatingActionButtonPadding,
                        bottom = if (scrollBarShouldShow.value) scrollBarHeight + floatingActionButtonPadding
                        else floatingActionButtonPadding
                    ),
                containerColor = LocalColors.current.inputVariant,
                onClick = { viewModel.expandAddItem() }
            ) {
                Icon(
                    painter = Resources.getIcon("sign/plus/add40"),
                    contentDescription = Resources.getString("ct/open_add_item"),
                    tint = LocalColors.current.onInputVariant
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
        horizontalAlignment = Alignment.Start
    ) {
        item {
            Text(
                text = title,
                modifier = Modifier
                    .padding(20.dp),
                maxLines = 1,
                style = TextStyle(
                    textDecoration = TextDecoration.Underline,
                    fontStyle = FontStyle.Italic,
                    fontFamily = FontFamily.Default,
                    fontWeight = FontWeight.W600,
                    fontSize = 26.sp,
                    letterSpacing = 0.6.sp,
                    lineHeight = 24.sp,
                ),
                color = LocalColors.current.onMainBackground
            )
        }
        content()
        item {
            Spacer(Modifier.height(50.dp))
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
            modifier = Modifier
                .align(Alignment.BottomStart)
                .height(scrollBarHeight),
            adapter = rememberScrollbarAdapter(stateHorizontal),
            style = LocalScrollbarStyle.current.copy(
                unhoverColor = LocalColors.current.inputVariant,
                hoverColor = LocalColors.current.inputVariant
            )
        )
    }
}