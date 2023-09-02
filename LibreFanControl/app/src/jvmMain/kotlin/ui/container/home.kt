package ui.container

import FState
import androidx.compose.animation.*
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import ui.addItem.ChoiceState
import ui.addItem.addItem
import ui.settings.drawer.drawerContent


@Composable
fun home() {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()


    ModalNavigationDrawer(
        modifier = Modifier,
        drawerContent = {
            ModalDrawerSheet{
                drawerContent(
                    drawerState = drawerState,
                    scope = scope
                )
            }

        },
        drawerState = drawerState,
        gesturesEnabled = true

    ) {


        CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {


                    val visibleState = remember { MutableTransitionState(FState.ui.addItemExpanded.value) }
                    visibleState.targetState = FState.ui.addItemExpanded.value

                    val choiceState = remember { mutableStateOf(ChoiceState()) }

                    AnimatedContent(
                        targetState = visibleState.targetState,
                        transitionSpec = {
                            ContentTransform(
                                targetContentEnter =
                                slideInHorizontally(
                                    tween(durationMillis = 1000),
                                    initialOffsetX = {
                                        it
                                    }
                                ),
                                initialContentExit =
                                slideOutHorizontally(
                                    tween(durationMillis = 1000),
                                    targetOffsetX = {
                                        it
                                    }
                                )

                            )
                        }
                    ) {
                        if (it) {
                            Column(
                                modifier = Modifier
                                    .width(260.dp)
                            ) {
                                topBarAddItem()
                                addItem(choiceState)
                            }
                        }
                    }

                    Column {
                        topBarBody(
                            onNavigationIconClick = {
                                scope.launch { drawerState.open() }
                            }
                        )
                        body(visibleState)
                    }
                }
            }
        }

    }
}