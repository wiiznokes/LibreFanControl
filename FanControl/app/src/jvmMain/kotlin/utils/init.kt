package utils

import FState.hControls
import FState.hFans
import FState.hTemps
import FState.iControls
import FState.iFans
import FState.iTemps
import androidx.compose.runtime.Composable
import model.item.BaseI
import model.item.IControl
import model.item.IFan
import model.item.ITemp
import ui.configuration.confDialogs.confNotSaveDialog
import ui.configuration.confDialogs.newConfDialog
import ui.dialogs.errorDialog
import ui.dialogs.needAdminDialog


/**
 * init fan and temperature item list, used when there is no config at start.
 * Each sensor will be represented by one sensor item.
 */
fun initSensor() {
    println("initItem, no config")

    hControls.forEach { hControl ->
        iControls.add(
            IControl(
                name = hControl.name,
                id = BaseI.getAvailableId(
                    list = iControls.map { it.id },
                    prefix = BaseI.IControlPrefix
                ),
                controlId = hControl.id
            )
        )
    }

    hTemps.forEach { hTemp ->
        iTemps.add(
            ITemp(
                name = hTemp.name,
                id = BaseI.getAvailableId(
                    list = iTemps.map { it.id },
                    prefix = BaseI.ITempPrefix
                ),
                hTempId = hTemp.id
            )
        )
    }

    hFans.forEach { hFan ->
        iFans.add(
            IFan(
                name = hFan.name,
                id = BaseI.getAvailableId(
                    list = iFans.map { it.id },
                    prefix = BaseI.IFanPrefix
                ),
                hFanId = hFan.id
            )
        )
    }
}

@Composable
fun initDialogs() {
    needAdminDialog()
    errorDialog()
    confNotSaveDialog()
    newConfDialog()
}