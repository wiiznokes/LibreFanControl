package proto

import State
import model.hardware.HControl
import proto.generated.conf.PConf
import java.io.File

private const val CONF_DIR = "conf/conf"

class ConfHelper {

    companion object {
        private val settings = State.settings

        private val iControls = State.iControls
        private val iBehaviors = State.iBehaviors
        private val iTemps = State.iTemps
        private val iFans = State.iFans

        private val hControls = State.hControls

        private val hTemps = State.hTemps
        private val hFans = State.hFans


        fun loadConf(confId: String) {
            val pConf = with(getFile(confId)) {
                PConf.parseFrom(readBytes())
            }
            hControls.clear()
            hTemps.clear()
            hFans.clear()

            iControls.clear()
            iBehaviors.clear()
            iTemps.clear()
            iFans.clear()


            pConf.phControlsList.forEach {
                hControls.add(
                    HControl(
                        name =
                    )
                )
            }
        }

        fun writeConf(confId: String) {

        }


        private fun getFile(confId: String): File {
            val includeFolder = File(System.getProperty("compose.application.resources.dir"))
            return includeFolder.resolve("$CONF_DIR$confId")
        }
    }


}