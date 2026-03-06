@file:JvmName("-internal")

package net.ccbluex.fastutil

import it.unimi.dsi.fastutil.Arrays

/** Sorts [this] by corresponding [weights] in the index range [from, to). */
@PublishedApi
internal fun <E> Array<E>.sortByWeightsInPlace(from: Int, to: Int, weights: DoubleArray) {
    Arrays.quickSort(
        from,
        to,
        { a, b ->
            weights[a].compareTo(weights[b])
        },
        { a, b ->
            this.swap(a, b)
            weights.swap(a, b)
        },
    )
}
