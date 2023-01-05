package ui.screen.drawer

import ui.screen.drawer.firstView.DonateSettingItem
import ui.screen.drawer.firstView.SettingItem


enum class SettingType {
    TIME_UPDATE,
    LANGUAGE,
    THEME,
    UNSPECIFIED
}


val mainSetting = listOf(
    SettingItem(
        title = "time between update",
        subTitle = "2",
        icon = "history",
        contentDescription = "",
        type = SettingType.TIME_UPDATE
    ),
    SettingItem(
        title = "language",
        subTitle = "en",
        icon = "translate",
        contentDescription = "",
        type = SettingType.LANGUAGE
    ),
    SettingItem(
        title = "theme",
        subTitle = "dark",
        icon = "dark_mode",
        contentDescription = "",
        type = SettingType.THEME
    )
)

val donate = DonateSettingItem(
    title = "support the project",
    icon = "attach_money",
    contentDescription = ""
)

val secondSetting = listOf(
    SettingItem(
        title = "info",
        subTitle = "dive into app working",
        icon = "info",
        contentDescription = ""
    ),
    SettingItem(
        title = "help",
        subTitle = "help",
        icon = "help",
        contentDescription = ""
    )
)


enum class Theme {
    SYSTEME,
    DARK,
    LIGHT
}

class DrawerVM {

    fun onTimeUpdate() {

    }

    fun onLanguage() {

    }

    fun onTheme(theme: Theme) {

    }
}