package settings

import proto.generated.setting.Languages
import proto.generated.setting.nullableId
import proto.generated.setting.setting
import proto.generated.update.update
import proto.generated.update.updateList

class SettingHelper {



    fun initSetting() {
        setting {
            language = Languages.EN
            configId = nullableId {
                null_
            }

        }
    }
}