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
import java.util.ArrayList
import java.util.concurrent.TimeUnit
import kotlin.streams.toList

@State(Scope.Thread)
@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
open class MapTransformBenchmark {
    @Param("1000", "100000", "1000000")
    var size: Int = 0

    private lateinit var input: ArrayList<Int>

    @Setup
    fun setup() {
        input = ArrayList(size)
        repeat(size) { input.add(it) }
    }

    @Benchmark
    fun mapToArrayAsList(bh: Blackhole) {
        bh.consume(input.mapToArray { it + 1 }.asList())
    }

    @Benchmark
    fun collectionMap(bh: Blackhole) {
        bh.consume(input.map { it + 1 })
    }

    @Benchmark
    fun sequenceMapToList(bh: Blackhole) {
        bh.consume(input.asSequence().map { it + 1 }.toList())
    }

    @Benchmark
    fun streamMapToList(bh: Blackhole) {
        bh.consume(input.stream().map { it + 1 }.toList())
    }
}
