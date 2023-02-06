package configuration


import settings.DIR_CONF
import java.io.File


private const val PREFIX_NEW_CONF = "config"
private const val SUFFIX_NEW_CONF = ".json"

class LoadConfigException : Exception()

class Configuration {
    companion object {
        /**
         * load configuration
         */
        fun loadConfig(
            configId: Long,
        ) {

        }


        fun deleteConfig(configId: Long) {
            val file = getFile(configId)
            file.delete()
        }

        fun saveConfig(
            configuration: ConfigurationModel,
        ) {



        }

        private fun getFile(id: Long): File {
            val includeFolder = File(System.getProperty("compose.application.resources.dir"))

            return includeFolder.resolve(DIR_CONF + PREFIX_NEW_CONF + id + SUFFIX_NEW_CONF)
        }
    }
}