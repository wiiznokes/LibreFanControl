package proto

import State.iBehaviors
import State.iControls
import State.iFans
import State.iTemps
import State.settings
import external.OS
import external.OsException
import external.getOS
import model.ConfInfo
import model.ItemType
import model.item.*
import proto.generated.conf.*
import proto.generated.setting.pConfInfo
import java.io.File

private const val CONF_DIR = "conf/"

class ProtoException(msg: String) : Exception(msg)

class ConfHelper {

    companion object {

        fun loadConf(confId: String) {
            val pConf = with(getConfFile(confId)) {
                PConf.parseFrom(readBytes())
            }

            parsePConf(pConf).let {
                iControls.apply {
                    clear()
                    addAll(it.iControls)
                }
                iBehaviors.apply {
                    clear()
                    addAll(it.iBehaviors)
                }
                iTemps.apply {
                    clear()
                    addAll(it.iTemps)
                }
                iFans.apply {
                    clear()
                    addAll(it.iFans)
                }
            }
        }


        fun writeConf(confId: String) {
            createPConf(
                Conf(
                    confInfo = settings.confInfoList.first {
                        it.id == confId
                    },
                    iControls = iControls,
                    iBehaviors = iBehaviors,
                    iTemps = iTemps,
                    iFans = iFans
                )
            ).let {
                with(getConfFile(confId)) {
                    writeBytes(it.toByteArray())
                }
            }
        }


        private fun getConfFile(confId: String): File = File(System.getProperty("compose.application.resources.dir"))
            .resolve("$CONF_DIR$confId")


        data class Conf(
            val confInfo: ConfInfo,
            val os: OS = getOS(),
            val iControls: MutableList<IControl> = mutableListOf(),
            val iBehaviors: MutableList<BaseIBehavior> = mutableListOf(),
            val iTemps: MutableList<BaseITemp> = mutableListOf(),
            val iFans: MutableList<IFan> = mutableListOf(),
        )


        fun parsePConf(pConf: PConf): Conf {

            val conf = Conf(
                confInfo = ConfInfo(
                    id = pConf.pConfInfo.pId,
                    name = pConf.pConfInfo.pName
                )
            )

            pConf.piControlsList.forEach { pControl ->
                conf.iControls.add(
                    IControl(
                        name = pControl.pName,
                        id = pControl.pId,
                        type = when (pControl.pType) {
                            PIControlTypes.I_C_FAN -> ItemType.ControlType.I_C_FAN
                            else -> throw ProtoException("unknown control type")
                        },
                        iBehaviorId = nullableToNull(pControl.piBehaviorId),
                        isAuto = pControl.pIsAuto,
                        controlId = nullableToNull(pControl.phControlId),
                    )
                )
            }

            pConf.piBehaviorsList.forEach { pBehavior ->
                conf.iBehaviors.add(
                    when (pBehavior.pType) {
                        PIBehaviorTypes.I_B_FLAT ->
                            IFlat(
                                name = pBehavior.pName,
                                id = pBehavior.pId,
                                value = pBehavior.pFlat.pValue
                            )


                        PIBehaviorTypes.I_B_LINEAR -> pBehavior.pLinear.let {
                            ILinear(
                                name = pBehavior.pName,
                                id = pBehavior.pId,
                                tempId = nullableToNull(it.pTempId),
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
                                tempId = nullableToNull(it.pTempId),
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
                conf.iTemps.add(
                    when (pTemp.pType) {
                        PITempTypes.I_S_TEMP -> ITemp(
                            name = pTemp.pName,
                            id = pTemp.pId,
                            hTempId = nullableToNull(pTemp.piSimpleTemp.phTempId)
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

            pConf.piFansList.forEach { pFan ->
                conf.iFans.add(
                    IFan(
                        name = pFan.pName,
                        id = pFan.pId,
                        hFanId = nullableToNull(pFan.phFanId)
                    )
                )
            }

            return conf
        }


        fun createPConf(conf: Conf) = pConf {

            pConfInfo = pConfInfo {
                pId = conf.confInfo.id
                pName = conf.confInfo.name.value
            }
            pOsType = when (conf.os) {
                OS.windows -> POsTypes.WINDOWS
                OS.linux -> POsTypes.LINUX
                OS.unsupported -> throw OsException("Os unsupported")
            }

            conf.iControls.forEach { iControl ->
                pIControls.add(pIControl {
                    pName = iControl.name.value
                    pId = iControl.id
                    pType = PIControlTypes.I_C_FAN
                    pIBehaviorId = nullToNullable(iControl.iBehaviorId.value)
                    pIsAuto = iControl.isAuto.value
                    pHControlId = nullToNullable(iControl.hControlId.value)
                }
                )
            }

            conf.iBehaviors.forEach { iBehavior ->
                pIBehaviors.add(
                    pIBehavior {
                        pName = iBehavior.name.value
                        pId = iBehavior.id
                        when (iBehavior) {
                            is IFlat -> {
                                pType = PIBehaviorTypes.I_B_FLAT
                                pFlat = pFlat {
                                    pValue = iBehavior.value.value
                                }
                            }

                            is ILinear -> {
                                pType = PIBehaviorTypes.I_B_LINEAR
                                pLinear = pLinear {
                                    pTempId = nullToNullable(iBehavior.hTempId.value)
                                    pMinTemp = iBehavior.minTemp.value
                                    pMaxTemp = iBehavior.maxTemp.value
                                    pMinFanSpeed = iBehavior.minFanSpeed.value
                                    pMaxFanSpeed = iBehavior.maxFanSpeed.value
                                }
                            }

                            is ITarget -> {
                                pType = PIBehaviorTypes.I_B_TARGET
                                pTarget = pTarget {
                                    pTempId = nullToNullable(iBehavior.hTempId.value)
                                    pIdleTemp = iBehavior.idleTemp.value
                                    pLoadTemp = iBehavior.loadTemp.value
                                    pIdleFanSpeed = iBehavior.idleFanSpeed.value
                                    pLoadFanSpeed = iBehavior.loadFanSpeed.value
                                }
                            }
                        }
                    }
                )
            }

            conf.iTemps.forEach { iTemp ->
                pITemps.add(
                    pITemp {
                        pName = iTemp.name.value
                        pId = iTemp.id
                        when (iTemp) {
                            is ITemp -> {
                                pType = PITempTypes.I_S_TEMP
                                pISimpleTemp = pISimpleTemp {
                                    pHTempId = nullToNullable(iTemp.hTempId.value)
                                }
                            }

                            is ICustomTemp -> {
                                pType = PITempTypes.I_S_CUSTOM_TEMP
                                pICustomTemp = pIcustomTemp {
                                    pType = when (iTemp.customTempType.value) {
                                        CustomTempType.average -> PCustomTempTypes.AVERAGE
                                        CustomTempType.max -> PCustomTempTypes.MAX
                                        CustomTempType.min -> PCustomTempTypes.MIN
                                    }

                                    iTemp.hTempIds.forEach { id ->
                                        pHTempIds.add(id)
                                    }
                                }
                            }
                        }
                    }
                )
            }

            conf.iFans.forEach { iFan ->
                pIFans.add(
                    pIFan {
                        pName = iFan.name.value
                        pId = iFan.id
                        pType = PIFanTypes.I_S_FAN
                        pHFanId = nullToNullable(iFan.hFanId.value)
                    }
                )
            }
        }
    }
}