package ui.screen.drawer

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.sp
import ui.utils.Resources


@Composable
fun drawer() {

    Text(
        text = Resources.getString("title/setting"),
        fontSize = 40.sp
    )

}