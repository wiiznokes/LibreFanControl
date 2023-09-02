package settingSlidingWindows

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import settingSlidingWindows.utils.getIcon

internal class SettingScopeImpl(
    private val map: MutableMap<Int, (@Composable () -> Unit)?>,
    private val list: MutableList<@Composable () -> Unit>,
    private val settingState: SettingState,
    private val _settingColors: SettingColors,
    private val settingTextStyle: SettingTextStyle,
) : SettingScope {

    private var size = 0

    override fun header(
        title: String,
        settingColors: SettingColors?
    ) {
        list.add {
            baseHeader(
                title = title,
                settingColors = settingColors ?: _settingColors
            )
        }
    }


    override fun header(
        content: @Composable () -> Unit,
    ) {
        list.add(content)
    }

    @Composable
    private fun baseHeader(
        title: String,
        settingColors: SettingColors,
    ) {
        Row(
            modifier = Modifier
                .background(
                    color = settingColors.background
                )
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                modifier = Modifier
                    .padding(start = 25.dp, top = 40.dp, bottom = 40.dp),
                text = title,
                style = settingTextStyle.headerStyle,
                color = settingColors.onBackground
            )
        }
    }


    override fun item(
        settingColors: SettingColors?,
        icon: @Composable() (() -> Unit)?,
        title: String?,
        subTitle: String?,
        advanceIconButton: @Composable() (() -> Unit)?,
        showAdvanceIcon: Boolean,
        showTopLine: Boolean,
        onClick: (() -> Unit)?,
        advanceItemContent: @Composable() (AdvanceSettingScope.() -> Unit)?
    ) {
        val index = size
        list.add {
            baseItem(
                settingColors = settingColors ?: _settingColors,
                icon = icon,
                title = title,
                subTitle = subTitle,
                advanceIconButton = advanceIconButton,
                showAdvanceIcon = showAdvanceIcon,
                index = index,
                showTopLine = showTopLine,
                onClick = onClick
            )
        }
        if (advanceItemContent != null) {
            val advanceSettingScopeImpl = AdvanceSettingScopeImpl(
                settingState = settingState,
                _settingColors = _settingColors,
                settingTextStyle = settingTextStyle,
                _title = title
            )

            map[index] = {
                Column {
                    advanceSettingScopeImpl.advanceItemContent()
                }
            }
        } else {
            map[index] = null
        }

        size++
    }


    override fun item(
        content: @Composable (Int) -> Unit,
        advanceItemContent: (@Composable AdvanceSettingScope.() -> Unit)?,
    ) {
        val index = size
        list.add {
            content(index)
        }

        if (advanceItemContent != null) {
            val advanceSettingScopeImpl = AdvanceSettingScopeImpl(
                settingState = settingState,
                _settingColors = _settingColors,
                settingTextStyle = settingTextStyle
            )
            map[index] = {
                advanceSettingScopeImpl.advanceItemContent()
            }
        } else {
            map[index] = null
        }
        size++
    }


    @Composable
    private fun baseItem(
        settingColors: SettingColors,
        icon: @Composable (() -> Unit)?,
        title: String?,
        subTitle: String?,
        advanceIconButton: @Composable (() -> Unit)?,
        showAdvanceIcon: Boolean,
        index: Int,
        showTopLine: Boolean,
        onClick: (() -> Unit)?
    ) {
        if (showTopLine) {
            Divider(
                modifier = Modifier.fillMaxWidth(),
                color = settingColors.onContainer,
                thickness = 2.dp
            )
        }

        CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
            Row(
                modifier = Modifier
                    .background(
                        color = settingColors.container
                    )
                    .clickable(
                        onClick = {
                            onClick?.invoke()
                            settingState.openAdvance(index)
                        }
                    ),
                verticalAlignment = Alignment.CenterVertically
            ) {
                CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {

                    if (showAdvanceIcon) {
                        if (advanceIconButton != null) {
                            advanceIconButton()
                        } else {
                            IconButton(
                                onClick = { settingState.openAdvance(index) }
                            ) {
                                Icon(
                                    painter = getIcon("arrow/chevron/chevron_right${SettingDefaults.iconSize}"),
                                    contentDescription = null,
                                    tint = settingColors.onContainer
                                )
                            }
                        }
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = SettingDefaults.mediumPadding, end = SettingDefaults.smallPadding)
                            .padding(bottom = SettingDefaults.smallPadding),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        if (icon != null) {
                            icon()
                            Spacer(modifier = Modifier.width(SettingDefaults.mediumPadding))
                        }

                        Column(
                            verticalArrangement = Arrangement.Center
                        ) {
                            if (title != null) {
                                Text(
                                    text = title,
                                    style = settingTextStyle.itemTitleStyle,
                                    overflow = TextOverflow.Clip,
                                    maxLines = 1,
                                    color = settingColors.onContainer
                                )
                            }
                            /*
                            if (title != null && subTitle != null) {
                                Spacer(Modifier.height(SettingDefaults.smallPadding))
                            }

                             */

                            if (subTitle != null) {
                                Text(
                                    text = subTitle,
                                    style = settingTextStyle.itemSubTitleStyle,
                                    overflow = TextOverflow.Clip,
                                    maxLines = 2,
                                    color = settingColors.onContainer
                                )
                            }
                        }
                    }
                }
            }
        }

        Divider(
            modifier = Modifier.fillMaxWidth(),
            thickness = 2.dp,
            color = settingColors.onContainer
        )
    }


    override fun group(
        text: String,
        settingColors: SettingColors?,
    ) {
        list.add {
            baseGroup(
                text = text,
                settingColors = settingColors ?: _settingColors
            )
        }
    }

    override fun group(content: @Composable () -> Unit) {
        list.add(content)
    }

    @Composable
    private fun baseGroup(
        text: String,
        settingColors: SettingColors
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(settingColors.background)
        ) {
            Text(
                text = text,
                style = settingTextStyle.groupStyle,
                color = settingColors.onBackground,
                modifier = Modifier
                    .padding(top = SettingDefaults.largePadding, bottom = SettingDefaults.smallPadding)
            )
        }
    }

}