package utils

import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable


/**
 * filter the list with the given predicate
 * and create a foreach loop with the previous
 * index and the corresponding value
 */
fun <T> filterWithPreviousIndex(
    list: List<T>,
    predicate: (T) -> Boolean,
    forEachFiltered: (Int, T) -> Unit
) {
    val previousIndexList = mutableListOf<Int>()

    for (i in list.indices) {
        if (predicate(list[i]))
            previousIndexList.add(i)
    }

    previousIndexList.forEach {
        forEachFiltered(it, list[it])
    }
}


fun <T> LazyListScope.filterWithPreviousIndexComposable(
    list: List<T>,
    predicate: (T) -> Boolean,
    content: @Composable (Int, T) -> Unit
) {
    val previousIndexList = mutableListOf<Int>()

    list.filterIndexed { index, control ->
        if (predicate(control)) {
            previousIndexList.add(index)
            true
        } else false
    }.let {
        itemsIndexed(it) { index, value ->
            content(previousIndexList[index], value)
        }
    }
}