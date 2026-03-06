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

@State(Scope.Thread)
@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
open class LfuCacheBenchmark {
    @Param("1000", "100000", "1000000")
    var size: Int = 0

    private lateinit var cache: LfuCache<String, Int>
    private var readIndex: Int = 0
    private var writeIndex: Int = 0

    @Setup
    fun setup() {
        val capacity = (size / 2).coerceAtLeast(16)
        cache = LfuCache(capacity)
        repeat(capacity) { i ->
            cache["seed-$i"] = i
        }
        readIndex = 0
        writeIndex = capacity
    }

    @Benchmark
    fun getHotPath(bh: Blackhole) {
        val key = "seed-${readIndex and 127}"
        readIndex++
        bh.consume(cache[key])
    }

    @Benchmark
    fun putWithEviction(bh: Blackhole) {
        val key = "new-$writeIndex"
        cache[key] = writeIndex
        writeIndex++
        bh.consume(cache.size)
    }
}
