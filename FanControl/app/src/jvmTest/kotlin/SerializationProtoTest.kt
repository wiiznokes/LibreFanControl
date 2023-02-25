import proto.SettingsHelper
import ui.settings.ConfInfo
import ui.settings.Languages
import ui.settings.Settings
import ui.settings.Themes
import kotlin.test.Test
import kotlin.test.assertEquals

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

    private fun makeTest(settings1: Settings) {
        val pSettings = SettingsHelper.createPSetting(settings1)
        val settings2 = SettingsHelper.parsePSetting(pSettings)

        assertEquals(settings1.language.value, settings2.language.value)
        assertEquals(settings1.confId.value, settings2.confId.value)
        settings1.confInfoList.forEachIndexed { index, confInfo1 ->
            val confInfo2 = settings2.confInfoList[index]
            assertEquals(confInfo1.id, confInfo2.id)
            assertEquals(confInfo1.name.value, confInfo2.name.value)
        }
        assertEquals(settings1.updateDelay.value, settings2.updateDelay.value)
        assertEquals(settings1.theme.value, settings2.theme.value)
        assertEquals(settings1.firstStart.value, settings2.firstStart.value)
        assertEquals(settings1.launchAtStartUp.value, settings2.launchAtStartUp.value)
        assertEquals(settings1.degree.value, settings2.degree.value)
    }

    @Test
    fun settingsTest() {
        makeTest(Settings())

        makeTest(settingsCustom)
    }

}