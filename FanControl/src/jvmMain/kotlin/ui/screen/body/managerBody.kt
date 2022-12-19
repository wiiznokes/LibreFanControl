package ui.screen.body

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material.IconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.WindowState
import ui.screen.body.behaviorList.behaviorList
import ui.screen.body.controlList.controlList
import ui.screen.body.fanList.fanList
import ui.screen.body.tempList.tempList
import ui.utils.Resources

@Composable
fun body(
    editModeActivated: MutableState<Boolean>,
    addItemExpanded: MutableState<Boolean>,
    windowState: WindowState
) {

    val viewModel = BodyViewModel()

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
                    title = Resources.getString("title/control"),
                    windowState = windowState
                ) {
                    controlList(editModeActivated.value)
                }
                itemsList(
                    title = Resources.getString("title/behavior"),
                    windowState = windowState
                ) {
                    behaviorList(editModeActivated.value)
                }
                itemsList(
                    title = Resources.getString("title/fan"),
                    windowState = windowState
                ) {
                    fanList(editModeActivated.value)
                }
                itemsList(
                    title = Resources.getString("title/temp"),
                    windowState = windowState
                ) {
                    tempList(editModeActivated.value)
                }
            }
        }

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
fun itemsList(
    title: String,
    windowState: WindowState,
    content: LazyListScope.() -> Unit
) {
    val density = LocalDensity.current

    val width = 350.dp
    var height by remember {
        mutableStateOf(0.dp)
    }
    val scale = 0.7f

    val errorFactor = 1.4f


    val hasMeasured = remember(
        windowState.size.height
    ) {
        mutableStateOf(false)
    }

    val modifier = when (hasMeasured.value) {
        false -> {
            Modifier
                .onGloballyPositioned { coordinates ->
                    height = with(density) { coordinates.size.height.toDp() } * errorFactor
                    hasMeasured.value = true
                }
        }

        true -> {
            Modifier
                .size(width = width * scale, height = height * scale)
                .requiredSize(width = width, height = height)
                .scale(scale)
        }
    }

    LazyColumn(
        modifier = modifier
    ) {
        item {
            Text(
                text = title,
                color = MaterialTheme.colorScheme.onSurface,
                fontSize = 40.sp
            )
            Spacer(
                modifier = Modifier
                    .height(20.dp)
                    .scale(0.7f)
            )
        }
        content()
    }
}


@Composable
fun BoxScope.scrollableBox(
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