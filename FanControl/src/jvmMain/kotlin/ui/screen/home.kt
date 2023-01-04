package ui.screen

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import ui.screen.addItem.ChoiceType
import ui.screen.addItem.addItem
import ui.screen.body.body
import ui.screen.drawer.drawer
import ui.screen.topBar.topBarAddItem
import ui.screen.topBar.topBarBody


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun home() {
    val viewModel = HomeVM()
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()


    ModalNavigationDrawer(
        modifier = Modifier,
        drawerContent = {
            drawer(
                drawerState = drawerState
            )
        },
        drawerState = drawerState,
        gesturesEnabled = true,
        drawerContainerColor = MaterialTheme.colorScheme.inverseSurface,
        drawerContentColor = MaterialTheme.colorScheme.inverseOnSurface,

    ) {
        val addItemExpanded = viewModel.addItemExpanded.collectAsState()


        CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {

                    val currentChoiceType: MutableState<ChoiceType> = remember {
                        mutableStateOf(ChoiceType.BEHAVIOR)
                    }

                    if (addItemExpanded.value) {
                        Column(
                            modifier = Modifier
                                .width(250.dp)
                        ) {
                            topBarAddItem()
                            addItem(currentChoiceType)
                        }
                    }

                    Column {
                        topBarBody(
                            onNavigationIconClick = {
                                scope.launch { drawerState.open() }
                            }
                        )
                        body()
                    }
                }
            }
        }

    }
}












