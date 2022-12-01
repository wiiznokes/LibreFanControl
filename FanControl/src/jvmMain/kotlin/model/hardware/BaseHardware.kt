package model.hardware

import model.BaseItem

interface BaseHardware: BaseItem {


    var name: String?
    var maxValue: Int
    var minValue: Int

    var libName: String
    var value: Int
}