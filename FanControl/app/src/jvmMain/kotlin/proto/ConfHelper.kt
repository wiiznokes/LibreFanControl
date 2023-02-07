package proto

import State
import model.ItemType
import model.item.*
import proto.generated.conf.*
import java.io.File

private const val CONF_DIR = "conf/conf"

class ProtoException(msg: String) : Exception(msg)

class ConfHelper {

    companion object {
        private val settings = State.settings

        private val iControls = State.iControls
        private val iBehaviors = State.iBehaviors
        private val iTemps = State.iTemps
        private val iFans = State.iFans


        fun loadConf(confId: String) {
            val pConf = with(getFile(confId)) {
                PConf.parseFrom(readBytes())
            }

            iControls.clear()
            iBehaviors.clear()
            iTemps.clear()
            iFans.clear()

            pConf.piControlsList.forEach { pControl ->
                iControls.add(
                    IControl(
                        name = pControl.pName,
                        id = pControl.pId,
                        type = when (pControl.pType) {
                            PIControlTypes.I_C_FAN -> ItemType.ControlType.I_C_FAN
                            else -> throw ProtoException("unknown control type")
                        },
                        iBehaviorId = pControl.pIBehaviorIdOrNull.let { it?.pId },
                        isAuto = pControl.pIsAuto,
                        controlId = pControl.pHControlIdOrNull.let { it?.pId }
                    )
                )
            }

            pConf.piBehaviorsList.forEach { pBehavior ->
                iBehaviors.add(
                    when (pBehavior.pType) {
                        PIBehaviorTypes.I_B_FLAT ->
                            IFlat(
                                name = pBehavior.pName,
                                id = pBehavior.pId
                            )


                        PIBehaviorTypes.I_B_LINEAR -> pBehavior.pLinear.let {
                            ILinear(
                                name = pBehavior.pName,
                                id = pBehavior.pId,
                                tempId = it.pTempIdOrNull.let { id -> id?.pId },
                                minTemp = it.pMinTemp,
                                maxTemp = it.pMaxTemp,
                                minFanSpeed = it.pMinFanSpeed,
                                maxFanSpeed = it.pMaxFanSpeed
                            )
                        }


                        PIBehaviorTypes.I_B_TARGET -> pBehavior.pTarget.let {
                            ITarget(
                                name = pBehavior.pName,
                                id = pBehavior.pId,
                                tempId = it.pTempIdOrNull.let { id -> id?.pId },
                                idleTemp = it.pIdleTemp,
                                loadTemp = it.pLoadTemp,
                                idleFanSpeed = it.pIdleFanSpeed,
                                loadFanSpeed = it.pLoadFanSpeed
                            )
                        }

                        else -> throw ProtoException("unknown behavior type")
                    }
                )
            }

            pConf.piTempsList.forEach { pTemp ->
                iTemps.add(
                    when (pTemp.pType) {
                        PITempTypes.I_S_TEMP -> ITemp(
                            name = pTemp.pName,
                            id = pTemp.pId,
                            hTempId = pTemp.piSimpleTemp.pHTempIdOrNull.let { it?.pId }
                        )

                        PITempTypes.I_S_CUSTOM_TEMP -> pTemp.piCustomTemp.let {
                            ICustomTemp(
                                name = pTemp.pName,
                                id = pTemp.pId,
                                customTempType = when (it.pType) {
                                    PCustomTempTypes.AVERAGE -> CustomTempType.average
                                    PCustomTempTypes.MAX -> CustomTempType.max
                                    PCustomTempTypes.MIN -> CustomTempType.min
                                    else -> throw ProtoException("unknown customTempType")
                                },
                                hTempIds = it.phTempIdsList
                            )
                        }

                        else -> throw ProtoException("unknown temp type")
                    }
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