package ui.component

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun itemsList(
    title: String,
    content: LazyListScope.() -> Unit,
) {
    LazyColumn {
        item {
            Text(
                text = title,
                color = MaterialTheme.colorScheme.onSurface,
                fontSize = 40.sp
            )
            Spacer(
                modifier = Modifier
                    .height(20.dp)
            )
        }
        content()
    }
}