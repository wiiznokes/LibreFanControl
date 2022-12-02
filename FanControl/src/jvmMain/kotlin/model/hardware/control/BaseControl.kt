package model.hardware.control

import model.hardware.BaseHardware

interface BaseControl : BaseHardware {
    var isAuto: Boolean
    var behaviorId: String?
}