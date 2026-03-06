package net.ccbluex.fastutil

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertNull
import kotlin.test.assertTrue

class WeightedTerminalTest {
    private val values = listOf("bbb", "a", "cc", "dd", "ee", "fff")
    private val lengthWeight: (String) -> Double = { it.length.toDouble() }

    private fun assertSortedByWeight(result: List<String>) {
        val weights = result.map(lengthWeight)
        assertTrue(weights.zipWithNext().all { (a, b) -> a <= b }, "result is not sorted by weight ascending")
    }

    private fun assertSameElementsIgnoringOrder(
        expected: List<String>,
        actual: List<String>,
    ) {
        assertEquals(expected.groupingBy { it }.eachCount(), actual.groupingBy { it }.eachCount())
    }

    @Test
    fun `weighted filter sorted keeps ascending order`() {
        val actual = values.weightedFilterSortedBy(lengthWeight)
        assertSortedByWeight(actual)
        assertSameElementsIgnoringOrder(values, actual)
    }

    @Test
    fun `weighted filter sorted at most matches filtering semantics and sequence parity`() {
        val max = 2.0
        val baselineFiltered = values.filter { lengthWeight(it) <= max }

        val iterableResult = values.weightedFilterSortedByAtMost(max, lengthWeight)
        val sequenceResult = values.asSequence().weightedFilterSortedByAtMost(max, lengthWeight)

        assertSortedByWeight(iterableResult)
        assertSameElementsIgnoringOrder(baselineFiltered, iterableResult)
        assertEquals(iterableResult, sequenceResult)
    }

    @Test
    fun `weighted filter sorted at least matches sequence parity`() {
        val min = 2.0
        val baselineFiltered = values.filter { lengthWeight(it) >= min }
        val iterableResult = values.weightedFilterSortedByAtLeast(min, lengthWeight)
        val sequenceResult = values.asSequence().weightedFilterSortedByAtLeast(min, lengthWeight)

        assertSortedByWeight(iterableResult)
        assertSameElementsIgnoringOrder(baselineFiltered, iterableResult)
        assertEquals(iterableResult, sequenceResult)
    }

    @Test
    fun `weighted filter sorted in uses inclusive bounds and validates range`() {
        val inRange = values.weightedFilterSortedByIn(2.0, 2.0, lengthWeight)
        assertSortedByWeight(inRange)
        assertSameElementsIgnoringOrder(listOf("cc", "dd", "ee"), inRange)

        assertFailsWith<IllegalArgumentException> {
            values.weightedFilterSortedByIn(3.0, 2.0, lengthWeight)
        }
        assertFailsWith<IllegalArgumentException> {
            values.asSequence().weightedFilterSortedByIn(3.0, 2.0, lengthWeight)
        }
    }

    @Test
    fun `weighted sorted terminals return empty list when no match`() {
        assertEquals(emptyList(), values.weightedFilterSortedByAtMost(0.0, lengthWeight))
        assertEquals(emptyList(), values.asSequence().weightedFilterSortedByAtMost(0.0, lengthWeight))
    }

    @Test
    fun `weighted min max without filters match baseline and keep first on tie`() {
        val withMinTie = listOf("aa", "bb", "ccc")
        val min = withMinTie.weightedMinByOrNull(lengthWeight)
        val max = withMinTie.weightedMaxByOrNull(lengthWeight)

        assertEquals("aa", min)
        assertEquals("ccc", max)
        assertEquals(withMinTie.minByOrNull(lengthWeight), min)
        assertEquals(withMinTie.maxByOrNull(lengthWeight), max)
    }

    @Test
    fun `weighted min max filtered variants match baseline and sequence parity`() {
        val minAtMostIterable = values.weightedMinByOrNullAtMost(2.0, lengthWeight)
        val minAtMostSequence = values.asSequence().weightedMinByOrNullAtMost(2.0, lengthWeight)
        val baselineMinAtMost = values.filter { lengthWeight(it) <= 2.0 }.minByOrNull(lengthWeight)
        assertEquals(baselineMinAtMost, minAtMostIterable)
        assertEquals(minAtMostIterable, minAtMostSequence)

        val maxAtLeastIterable = values.weightedMaxByOrNullAtLeast(2.0, lengthWeight)
        val maxAtLeastSequence = values.asSequence().weightedMaxByOrNullAtLeast(2.0, lengthWeight)
        val baselineMaxAtLeast = values.filter { lengthWeight(it) >= 2.0 }.maxByOrNull(lengthWeight)
        assertEquals(baselineMaxAtLeast, maxAtLeastIterable)
        assertEquals(maxAtLeastIterable, maxAtLeastSequence)

        val minInIterable = values.weightedMinByOrNullIn(2.0, 3.0, lengthWeight)
        val minInSequence = values.asSequence().weightedMinByOrNullIn(2.0, 3.0, lengthWeight)
        val baselineMinIn = values.filter { lengthWeight(it) in 2.0..3.0 }.minByOrNull(lengthWeight)
        assertEquals(baselineMinIn, minInIterable)
        assertEquals(minInIterable, minInSequence)

        val maxInIterable = values.weightedMaxByOrNullIn(2.0, 3.0, lengthWeight)
        val maxInSequence = values.asSequence().weightedMaxByOrNullIn(2.0, 3.0, lengthWeight)
        val baselineMaxIn = values.filter { lengthWeight(it) in 2.0..3.0 }.maxByOrNull(lengthWeight)
        assertEquals(baselineMaxIn, maxInIterable)
        assertEquals(maxInIterable, maxInSequence)
    }

    @Test
    fun `weighted min max terminals return null when no match or empty`() {
        val empty = emptyList<String>()
        assertNull(empty.weightedMinByOrNull(lengthWeight))
        assertNull(empty.weightedMaxByOrNull(lengthWeight))
        assertNull(empty.asSequence().weightedMinByOrNull(lengthWeight))
        assertNull(empty.asSequence().weightedMaxByOrNull(lengthWeight))

        assertNull(values.weightedMinByOrNullAtMost(0.0, lengthWeight))
        assertNull(values.weightedMaxByOrNullAtMost(0.0, lengthWeight))
        assertNull(values.asSequence().weightedMinByOrNullAtMost(0.0, lengthWeight))
        assertNull(values.asSequence().weightedMaxByOrNullAtMost(0.0, lengthWeight))
    }

    @Test
    fun `weighted min max in validates range`() {
        assertFailsWith<IllegalArgumentException> {
            values.weightedMinByOrNullIn(5.0, 1.0, lengthWeight)
        }
        assertFailsWith<IllegalArgumentException> {
            values.weightedMaxByOrNullIn(5.0, 1.0, lengthWeight)
        }
        assertFailsWith<IllegalArgumentException> {
            values.asSequence().weightedMinByOrNullIn(5.0, 1.0, lengthWeight)
        }
        assertFailsWith<IllegalArgumentException> {
            values.asSequence().weightedMaxByOrNullIn(5.0, 1.0, lengthWeight)
        }
    }
}
