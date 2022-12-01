package ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun home(viewModel: MainViewModel) {
    val uiStates = viewModel.uiState.collectAsState()

    Row(
        modifier = Modifier
            .fillMaxSize()
    ) {
        LazyColumn {
            stickyHeader {
                Text(
                    text = "Fan"
                )
                Spacer(
                    modifier = Modifier
                        .height(20.dp)
                )
            }
            items(uiStates.value.fanList) {
                Text(text = "${it.libName} = ${it.value}")
            }
        }

        LazyColumn {
            stickyHeader {
                Text(
                    text = "Temp"
                )
                Spacer(
                    modifier = Modifier
                        .height(20.dp)
                )
            }

            items(uiStates.value.tempList) {
                Text(text = "${it.libName} = ${it.value}")
            }
        }

        LazyColumn {
            stickyHeader {
                Text(
                    text = "Control"
                )
                Spacer(
                    modifier = Modifier
                        .height(20.dp)
                )
            }

            items(uiStates.value.controlList) {
                Text(text = "${it.libName} = ${it.value}")
            }
        }
    }
}





