package ui.component.windows

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun windows(
    content: @Composable () -> Unit
) {


    val stateVertical = rememberScrollState(0)
    val stateHorizontal = rememberScrollState(0)

    Box {


        Box(
            modifier = Modifier
                .fillMaxSize()
                //.verticalScroll(stateVertical)
                .horizontalScroll(stateHorizontal)
        ) {

            content()


        }


        /*
        VerticalScrollbar(
            modifier = Modifier.align(Alignment.CenterEnd)
                .fillMaxHeight(),
            adapter = rememberScrollbarAdapter(stateVertical)
        )

         */
    }

}