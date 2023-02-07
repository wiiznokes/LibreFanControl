import model.ConfInfo
import model.Languages
import model.Settings
import model.Themes
import proto.SettingsHelper
import kotlin.test.Test

class SerializationProtoTest {

    private val settingsCustom = Settings(
        language = Languages.fr,
        confId = "confId",
        confInfoList = listOf(ConfInfo(id = "confId", name = "name")),
        updateDelay = 10,
        theme = Themes.light,
        firstStart = false,
        launchAtStartUp = true,
        degree = false,
    )


    @Test
   fun settingTest () {

        val settings = Settings()

        val pSettings = SettingsHelper.createPSetting(settings)

        val returnedSettings = SettingsHelper.parsePSetting(pSettings)

   }


}