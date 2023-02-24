package proto

import Application.Api.api
import Application.Api.scope
import FState.iBehaviors
import FState.iControls
import FState.iFans
import FState.iTemps
import FState.settings
import external.OS
import external.OsException
import external.getOS
import kotlinx.coroutines.launch
import model.ConfInfo
import model.ItemType
import model.item.*
import proto.SettingsDir.getConfFile
import proto.generated.pConf.*
import proto.generated.pSettings.pConfInfo


class ProtoException(msg: String) : Exception(msg)

class ConfHelper {

    companion object {

        fun loadConf(confId: String) {
            val pConf = PConf.parseFrom(getConfFile(confId).readBytes())

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


        fun writeConf(confId: String, confName: String, notifyService: Boolean = true) {
            createPConf(getConf(confId, confName)).let {
                with(getConfFile(confId)) {
                    writeBytes(it.toByteArray())
                }
            }
            if (notifyService) {
                scope.launch {
                    api.settingsAndConfChange()
                }
            }
        }

        fun removeConf(confId: String) {
            getConfFile(confId).delete()
        }

        fun isConfDifferent(confId: String, confName: String): Boolean {
            return createPConf(getConf(confId, confName)) != PConf.parseFrom(getConfFile(confId).readBytes())
        }


        data class Conf(
            val confInfo: ConfInfo,
            val os: OS = getOS(),
            val iControls: MutableList<IControl> = mutableListOf(),
            val iBehaviors: MutableList<BaseIBehavior> = mutableListOf(),
            val iTemps: MutableList<BaseITemp> = mutableListOf(),
            val iFans: MutableList<IFan> = mutableListOf(),
        )

        fun getConf(confId: String, confName: String): Conf = Conf(
            confInfo = ConfInfo(
                id = confId,
                name = confName
            ),
            iControls = iControls,
            iBehaviors = iBehaviors,
            iTemps = iTemps,
            iFans = iFans
        )


        fun parsePConf(pConf: PConf): Conf {

            val conf = Conf(
                confInfo = ConfInfo(
                    id = pConf.pConfInfo.pId,
                    name = pConf.pConfInfo.pName
                )
            )

            pConf.piControlsList.forEach { pIControl ->
                conf.iControls.add(
                    IControl(
                        name = pIControl.pName,
                        id = pIControl.pId,
                        type = when (pIControl.pType) {
                            PIControlTypes.I_C_FAN -> ItemType.ControlType.I_C_FAN
                            else -> throw ProtoException("unknown control type")
                        },
                        iBehaviorId = nullableToNull(pIControl.piBehaviorId),
                        isAuto = pIControl.pIsAuto,
                        controlId = nullableToNull(pIControl.phControlId),
                    )
                )
            }

            pConf.piBehaviorsList.forEach { pIBehavior ->
                conf.iBehaviors.add(
                    when (pIBehavior.pType) {
                        PIBehaviorTypes.I_B_FLAT ->
                            IFlat(
                                name = pIBehavior.pName,
                                id = pIBehavior.pId,
                                value = pIBehavior.pFlat.pValue
                            )


                        PIBehaviorTypes.I_B_LINEAR -> pIBehavior.pLinear.let {
                            ILinear(
                                name = pIBehavior.pName,
                                id = pIBehavior.pId,
                                tempId = nullableToNull(it.pTempId),
                                minTemp = it.pMinTemp,
                                maxTemp = it.pMaxTemp,
                                minFanSpeed = it.pMinFanSpeed,
                                maxFanSpeed = it.pMaxFanSpeed
                            )
                        }


                        PIBehaviorTypes.I_B_TARGET -> pIBehavior.pTarget.let {
                            ITarget(
                                name = pIBehavior.pName,
                                id = pIBehavior.pId,
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

            pConf.piTempsList.forEach { pITemp ->
                conf.iTemps.add(
                    when (pITemp.pType) {
                        PITempTypes.I_S_TEMP -> ITemp(
                            name = pITemp.pName,
                            id = pITemp.pId,
                            hTempId = nullableToNull(pITemp.piSimpleTemp.phTempId)
                        )

                        PITempTypes.I_S_CUSTOM_TEMP -> pITemp.piCustomTemp.let {
                            ICustomTemp(
                                name = pITemp.pName,
                                id = pITemp.pId,
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

            pConf.piFansList.forEach { pIFan ->
                conf.iFans.add(
                    IFan(
                        name = pIFan.pName,
                        id = pIFan.pId,
                        hFanId = nullableToNull(pIFan.phFanId)
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