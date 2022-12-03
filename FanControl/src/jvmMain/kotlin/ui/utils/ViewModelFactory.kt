package ui.utils

import ui.screen.HomeViewModel
import ui.screen.addItem.behavior.AddBehaviorViewModel
import ui.screen.addItem.control.AddControlViewModel
import ui.screen.body.BodyViewModel
import ui.screen.body.behaviorList.BehaviorViewModel
import ui.screen.body.controlList.ControlViewModel
import ui.screen.body.fanList.FanViewModel
import ui.screen.body.fanList.fan.AddFanViewModel
import ui.screen.body.tempList.TempViewModel
import ui.screen.body.tempList.tem.AddTempViewModel

class ViewModelFactory() {

    companion object {

        var addFanViewModel: AddFanViewModel? = null
        var addTempViewModel: AddTempViewModel? = null
        var addControlViewModel: AddControlViewModel? = null
        var addBehaviorViewModel: AddBehaviorViewModel? = null


        var fanViewModel: FanViewModel? = null
        var tempViewModel: TempViewModel? = null
        var controlViewModel: ControlViewModel? = null
        var behaviorViewModel: BehaviorViewModel? = null

        var bodyViewModel: BodyViewModel? = null
        var homeViewModel: HomeViewModel? = null
    }
}