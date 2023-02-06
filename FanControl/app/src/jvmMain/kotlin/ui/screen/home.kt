package ui.screen

import androidx.compose.animation.*
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import ui.screen.addItem.addItem
import ui.screen.body.body
import ui.screen.drawer.drawerContent
import ui.screen.topBar.topBarAddItem
import ui.screen.topBar.topBarBody
import ui.theme.LocalColors
import ui.theme.LocalShapes


@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Composable
fun home() {
    val viewModel = HomeVM()
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()


    ModalNavigationDrawer(
        modifier = Modifier,
        drawerContent = {
            drawerContent(
                drawerState = drawerState,
                scope = scope
            )
        },
        drawerState = drawerState,
        gesturesEnabled = true

        ) {
        val addItemExpanded = viewModel.addItemExpanded.collectAsState()


        CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {

                    val visibleState = remember { MutableTransitionState(addItemExpanded.value) }
                    visibleState.targetState = addItemExpanded.value


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
                                addItem()
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












