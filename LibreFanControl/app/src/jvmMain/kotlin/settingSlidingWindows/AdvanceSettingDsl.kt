package settingSlidingWindows

import androidx.compose.runtime.Composable


interface AdvanceSettingScope {


    // https://issuetracker.google.com/issues/239435908?pli=1
    /**
     * Header of the advance setting windows. Displays a back arrow and title.
     * title and settingColors params are nullable because they can be retrieved
     * from SettingScope, but it's impossible to make default parameter
     * in a composable function inside an interface.
     * @param title title of header
     * @param settingColors colors used in the header
     */
    @Composable
    fun header(
        title: String?,
        settingColors: SettingColors?,
    )
}