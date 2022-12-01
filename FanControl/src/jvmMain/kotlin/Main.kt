// Copyright 2000-2021 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import ui.Event
import ui.MainViewModel
import ui.home


fun main() {
    val viewModel = MainViewModel()

    application {
        Window(
            title = "FanControl",
            onCloseRequest = {
                viewModel.onEvent(Event.Stop)
                (::exitApplication)()
            }

        ) {
            home(viewModel)
        }
    }

}


