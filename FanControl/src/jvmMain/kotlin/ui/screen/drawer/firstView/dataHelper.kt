package ui.screen.drawer.firstView

import settings.SettingsModel
import ui.screen.drawer.SettingType
import ui.utils.Resources


data class DonateSettingItem(
    val title: String,
    val icon: String,
    val type: SettingType = SettingType.DONATE
)

data class SettingItem(
    val title: String,
    val subTitle: String,
    val icon: String,
    val type: SettingType
)


fun getMainItemSetting(setting: SettingsModel) =
    listOf(
        SettingItem(
            title = Resources.getString("settings/update_delay"),
            subTitle = setting.updateDelay.toString(),
            icon = "settings/history",
            type = SettingType.TIME_UPDATE
        ),
        SettingItem(
            title = Resources.getString("settings/language"),
            subTitle = Resources.getString("language/${setting.language}"),
            icon = "settings/translate",
            type = SettingType.LANGUAGE
        ),
        SettingItem(
            title = Resources.getString("settings/theme"),
            subTitle = Resources.getString("theme/${setting.theme}"),
            icon = "settings/dark_mode",
            type = SettingType.THEME
        )
    )

fun getDonateItemSetting() =
    DonateSettingItem(
        title = Resources.getString("settings/donate"),
        icon = "settings/attach_money",
    )

fun getOtherItemSetting() =
    listOf(
        SettingItem(
            title = Resources.getString("settings/info"),
            subTitle = Resources.getString("settings/info_sub_title"),
            icon = "settings/info",
            type = SettingType.INFO
        ),
        SettingItem(
            title = Resources.getString("settings/help"),
            subTitle = Resources.getString("settings/help_sub_title"),
            icon = "settings/help",
            type = SettingType.HELP
        )
    )