package ui.container

import FState
import ServiceState
import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.IconButton
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.onPointerEvent
import androidx.compose.ui.unit.dp
import ui.component.managerText
import ui.configuration.confTopBar
import ui.theme.LocalColors
import utils.Resources


val topBarHeight = 45.dp

@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@Composable
fun topBarBody(
    onNavigationIconClick: () -> Unit,
) {
    MediumTopAppBar(
        modifier = Modifier
            .height(topBarHeight),
        title = {
            Row(
                modifier = Modifier
                    .fillMaxHeight(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Spacer(Modifier.width(20.dp))

                var active by remember { mutableStateOf(false) }

                var angle by remember { mutableStateOf(0f) }
                val rotation = remember { Animatable(angle) }

                LaunchedEffect(active) {
                    if (active) {
                        rotation.animateTo(
                            targetValue = angle + 360f,
                            animationSpec = infiniteRepeatable(
                                animation = tween(500, easing = LinearEasing),
                                repeatMode = RepeatMode.Restart
                            )
                        ) {
                            angle = value
                        }
                    } else {
                        if (angle > 0f) {
                            // Slow down rotation on pause
                            rotation.animateTo(
                                targetValue = angle + 300,
                                animationSpec = tween(
                                    durationMillis = 1250,
                                    easing = LinearOutSlowInEasing
                                )
                            ) {
                                angle = value
                            }
                        }
                    }
                }
                Icon(
                    modifier = Modifier
                        .onPointerEvent(PointerEventType.Enter) { active = true }
                        .onPointerEvent(PointerEventType.Exit) { active = false }
                        .graphicsLayer { rotationZ = angle },
                    painter = Resources.getIcon("topBar/toys_fan40"),
                    contentDescription = Resources.getString("title/app_name"),
                    tint = LocalColors.current.onMainTopBar
                )

                Spacer(Modifier.width(10.dp))

                managerText(
                    modifier = Modifier,
                    text = Resources.getString("title/app_name"),
                    style = MaterialTheme.typography.titleMedium,
                    color = LocalColors.current.onMainTopBar
                )
            }
        },
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = LocalColors.current.mainTopBar
        ),
        navigationIcon = {
            IconButton(
                onClick = {
                    onNavigationIconClick()
                }
            ) {
                Icon(
                    painter = Resources.getIcon("topBar/menu40"),
                    contentDescription = Resources.getString("ct/open_drawer"),
                    tint = LocalColors.current.onMainTopBar
                )
            }
        },
        actions = {
            Row(
                modifier = Modifier
                    .fillMaxHeight(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                when (FState.serviceState.value) {
                    ServiceState.WAIT_OPEN -> {


                        CircularProgressIndicator(
                            color = LocalColors.current.onMainTopBar
                        )

                        /*

                        val infiniteTransition = rememberInfiniteTransition()

                        val angle by infiniteTransition.animateFloat(
                            initialValue = 0f,
                            targetValue = 360f,
                            animationSpec = infiniteRepeatable(
                                animation = tween(1000, easing = LinearEasing),
                                repeatMode = RepeatMode.Restart
                            )
                        )

                        Icon(
                            modifier = Modifier
                                .padding(bottom = 5.dp, top = 1.dp)
                                .graphicsLayer { rotationZ = angle },
                            painter = Resources.getIcon("topBar/forward_media40"),
                            contentDescription = Resources.getString("ct/service_is_loading"),
                            tint = LocalColors.current.onMainTopBar
                        )*/
                    }

                    ServiceState.ERROR -> {
                        Icon(
                            modifier = Modifier,
                            painter = Resources.getIcon("topBar/error40"),
                            contentDescription = Resources.getString("ct/service_error"),
                            tint = LocalColors.current.error
                        )
                    }

                    ServiceState.OPEN -> {}
                }
                if (FState.serviceState.value != ServiceState.OPEN) {
                    Divider(
                        modifier = Modifier
                            .fillMaxHeight(0.8f)
                            .padding(horizontal = 10.dp)
                            .width(2.dp),
                        color = LocalColors.current.onMainTopBar
                    )
                }


                confTopBar()

                Divider(
                    modifier = Modifier
                        .fillMaxHeight(0.8f)
                        .padding(horizontal = 10.dp)
                        .width(2.dp),
                    color = LocalColors.current.onMainTopBar
                )

                IconButton(
                    onClick = {
                        FState.ui.editModeActivated.value = !FState.ui.editModeActivated.value
                    }
                ) {
                    Icon(
                        painter = Resources.getIcon("topBar/edit40"),
                        contentDescription = Resources.getString("ct/edit"),
                        modifier = Modifier
                            .padding(bottom = 5.dp, top = 1.dp),
                        tint = LocalColors.current.onMainTopBar
                    )
                }
            }
        }
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun topBarAddItem() {
    CenterAlignedTopAppBar(
        modifier = Modifier
            .height(topBarHeight),
        title = {
            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                managerText(
                    modifier = Modifier
                        .align(Alignment.Center),
                    text = Resources.getString("title/add_item"),
                    style = MaterialTheme.typography.titleSmall,
                    color = LocalColors.current.onSecondTopBar
                )
            }
        },
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = LocalColors.current.secondTopBar
        ),
        navigationIcon = {
            IconButton(
                onClick = {
                    FState.ui.addItemExpanded.value = false
                }
            ) {
                Icon(
                    painter = Resources.getIcon("topBar/forward40"),
                    contentDescription = Resources.getString("ct/close_add_item"),
                    tint = LocalColors.current.onSecondTopBar
                )
            }
        }
    )
}