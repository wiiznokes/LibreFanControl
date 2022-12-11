package ui.screen

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import ui.screen.addItem.addItem
import ui.screen.body.body
import ui.screen.drawer.drawer
import ui.screen.topBar.topBar


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun home() {

    val viewModel = HomeViewModel()


    ModalNavigationDrawer(
        modifier = Modifier,
        drawerContent = {
            drawer(

            )
        },
        drawerState = viewModel.drawerState.value,
        gesturesEnabled = true
    ) {
        val addItemExpanded = viewModel.addItemExpanded.value

        Column {
            topBar()

            // body + addItem
            Row(
                modifier = Modifier
                    .fillMaxSize()
            ) {


                var modifier = if (addItemExpanded.value)
                    Modifier.fillMaxHeight().fillMaxWidth(0.75f)
                else
                    Modifier.fillMaxSize()


                Box(
                    modifier = modifier
                        .background(MaterialTheme.colorScheme.background)
                ) {
                    val stateHorizontal = rememberScrollState(0)

                    val scrollBarShouldShow =
                        stateHorizontal.value != stateHorizontal.maxValue || stateHorizontal.value != 0

                    modifier = if (scrollBarShouldShow)
                        Modifier.padding(bottom = 20.dp)
                    else
                        Modifier

                    Box(
                        modifier = modifier
                            .horizontalScroll(stateHorizontal)

                    ) {
                        body(
                            editModeActivated = viewModel.editModeActivated.value
                        )
                    }

                    if (scrollBarShouldShow) {
                        HorizontalScrollbar(
                            modifier = Modifier.align(Alignment.BottomStart)
                                .height(20.dp)
                                .background(Color.Green),
                            adapter = rememberScrollbarAdapter(stateHorizontal)
                        )
                    }


                    // add button
                    if (!addItemExpanded.value) {
                        Button(
                            modifier = Modifier
                                .align(Alignment.BottomEnd)
                                .padding(20.dp),
                            onClick = {
                                viewModel.expandAddItem()
                            }
                        ) {
                            Text(
                                text = "Add"
                            )
                        }
                    }
                }

                // addItem
                if (addItemExpanded.value) {
                    Box(
                        Modifier.fillMaxSize().background(color = Color.Red)
                    ) {
                        addItem()
                    }
                }
            }
        }
    }
}












