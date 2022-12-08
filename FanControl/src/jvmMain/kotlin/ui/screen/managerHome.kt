package ui.screen

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ui.component.managerText
import ui.component.windows.windows
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

        Row {
            Column {
                topBar()

                Box(
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.background)
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
                            //.fillMaxSize()
                            .horizontalScroll(stateHorizontal)

                    ) {
                        body(
                            editModeActivated = viewModel.editModeActivated.value
                        )
                    }



                    if(!addItemExpanded.value) {
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


                    if (scrollBarShouldShow) {
                        HorizontalScrollbar(
                            modifier = Modifier.align(Alignment.BottomStart)
                                //.fillMaxWidth()
                                .height(20.dp)
                                .background(Color.Green),
                            adapter = rememberScrollbarAdapter(stateHorizontal)
                        )
                    }
                }
            }

            if(addItemExpanded.value) {
                Box(
                    Modifier.fillMaxSize().background(color = Color.Green)
                ) {
                    addItem()
                }
            }
        }





    }
}





