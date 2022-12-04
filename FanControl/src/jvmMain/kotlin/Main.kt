// Copyright 2000-2021 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.example.myapplication.ui.theme.FanControlTheme
import ui.screen.home


fun main() {

    val application = Application()
    application.onStart()

    application(
        exitProcessOnExit = true
    ) {
        Window(
            title = "FanControl",
            onCloseRequest = {
                application.onStop()
                (::exitApplication)()
            }

        ) {
            FanControlTheme {
                home()
            }
        }
    }

}


