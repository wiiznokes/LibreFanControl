package utils


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

    list.filterIndexed { index, control ->
        if (predicate(control)) {
            previousIndexList.add(index)
            true
        } else false
    }.forEachIndexed { index, value ->
        forEachFiltered(previousIndexList[index], value)
    }
}