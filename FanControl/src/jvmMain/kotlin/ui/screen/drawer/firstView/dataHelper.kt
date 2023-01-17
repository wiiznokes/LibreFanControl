package ui.screen.drawer.firstView

import settings.SettingsModel
import ui.utils.Resources


data class DonateSettingItem(
    val title: String,
    val icon: String,
)

data class SettingItem(
    val title: String,
    val subTitle: String,
    val icon: String,
)


fun getMainItemSetting(setting: SettingsModel) =
    listOf(
        SettingItem(
            title = Resources.getString("settings/update_delay"),
            subTitle = setting.updateDelay.toString(),
            icon = "settings/history40",
        ),
        SettingItem(
            title = Resources.getString("settings/language"),
            subTitle = Resources.getString("language/${setting.language}"),
            icon = "settings/translate40",
        ),
        SettingItem(
            title = Resources.getString("settings/theme"),
            subTitle = Resources.getString("theme/${setting.theme}"),
            icon = "settings/dark_mode40",
        )
    )

fun getDonateItemSetting() =
    DonateSettingItem(
        title = Resources.getString("settings/donate"),
        icon = "settings/attach_money40",
    )

fun getOtherItemSetting() =
    listOf(
        SettingItem(
            title = Resources.getString("settings/info"),
            subTitle = Resources.getString("settings/info_sub_title"),
            icon = "settings/info40",
        ),
        SettingItem(
            title = Resources.getString("settings/help"),
            subTitle = Resources.getString("settings/help_sub_title"),
            icon = "settings/help40",
        )
    )