package ui.component.windows

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

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