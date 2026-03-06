package net.ccbluex.fastutil

import org.openjdk.jmh.annotations.Benchmark
import org.openjdk.jmh.annotations.BenchmarkMode
import org.openjdk.jmh.annotations.Mode
import org.openjdk.jmh.annotations.OutputTimeUnit
import org.openjdk.jmh.annotations.Param
import org.openjdk.jmh.annotations.Scope
import org.openjdk.jmh.annotations.Setup
import org.openjdk.jmh.annotations.State
import org.openjdk.jmh.infra.Blackhole
import java.util.concurrent.TimeUnit
import java.util.function.ToDoubleFunction

@State(Scope.Thread)
@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
open class WeightedSortedListBenchmark {
    @Param("1000", "100000", "1000000")
    var size: Int = 0

    private lateinit var input: List<Int>
    private lateinit var preloaded: WeightedSortedList<Int>
    private val weighter = ToDoubleFunction<Int> { it.toDouble() }

    @Setup
    fun setup() {
        input = List(size) { size - it }
        preloaded = WeightedSortedList(weighter = weighter)
        preloaded.addAll(input)
    }

    @Benchmark
    fun addIncremental(bh: Blackhole) {
        val list = WeightedSortedList(weighter = weighter)
        for (value in input) {
            list.add(value)
        }
        bh.consume(list.size)
    }

    @Benchmark
    fun addAllBatch(bh: Blackhole) {
        val list = WeightedSortedList(weighter = weighter)
        list.addAll(input)
        bh.consume(list.size)
    }

    @Benchmark
    fun queryAndReadWeight(bh: Blackhole) {
        val key = size / 2
        bh.consume(preloaded.contains(key))
        bh.consume(preloaded.getDouble(key))
    }
}
