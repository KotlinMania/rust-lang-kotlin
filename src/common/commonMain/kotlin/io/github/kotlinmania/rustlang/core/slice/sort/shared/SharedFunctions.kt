package io.github.kotlinmania.rustlang.core.slice.sort.shared

/**
 * Finds a run of sorted elements starting at the beginning of the slice.
 *
 * Returns the length of the run, and a bool that is false when the run
 * is ascending, and true if the run strictly descending.
 */
internal fun <T> findExistingRun(v: List<T>, isLess: (T, T) -> Boolean): Pair<Int, Boolean> {
    val len = v.size
    if (len < 2) {
        return Pair(len, false)
    }
    // SAFETY: We checked that len >= 2, so 0 and 1 are valid indices.
    // This also means that runLen < len implies runLen and runLen - 1
    // are valid indices as well.
    return run {
        var runLen = 2
        val strictlyDescending = isLess(v.getUnchecked(1), v.getUnchecked(0))
        if (strictlyDescending) {
            while (runLen < len && isLess(v.getUnchecked(runLen), v.getUnchecked(runLen - 1))) {
                runLen += 1
            }
        } else {
            while (runLen < len && !isLess(v.getUnchecked(runLen), v.getUnchecked(runLen - 1))) {
                runLen += 1
            }
        }
        Pair(runLen, strictlyDescending)
    }
}
