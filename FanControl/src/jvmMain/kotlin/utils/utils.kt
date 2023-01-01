package utils

fun <T> filterWithPreviousIndex(
    list: List<T>,
    predicate: (T) -> Boolean,
    forEachFiltered: (Int, T) -> Unit
) {
    val previousIndexList = mutableListOf<Int>()

    list.filterIndexed { index, control ->
        if (predicate(control)) {
            previousIndexList.add(index)
            true
        } else false
    }.forEachIndexed { index, control ->
        forEachFiltered(previousIndexList[index], control)
    }
}