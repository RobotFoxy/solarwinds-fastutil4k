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

@State(Scope.Thread)
@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
open class PoolBenchmark {
    @Param("1000", "100000", "1000000")
    var size: Int = 0

    private lateinit var pool: Pool<StringBuilder>
    private lateinit var sink: MutableList<StringBuilder>

    @Setup
    fun setup() {
        pool = Pool({ StringBuilder() }, StringBuilder::clear)
        sink = ArrayList(64)

        // Pre-warm pool proportional to dataset scale.
        val warmCount = when {
            size <= 1_000 -> 16
            size <= 100_000 -> 64
            else -> 256
        }
        repeat(warmCount) {
            pool.recycle(StringBuilder())
        }
    }

    @Benchmark
    fun borrowRecycle(bh: Blackhole) {
        val sb = pool.borrow()
        sb.append('x')
        pool.recycle(sb)
        bh.consume(sb.length)
    }

    @Benchmark
    fun borrowIntoAndRecycleAll(bh: Blackhole) {
        sink.clear()
        pool.borrowInto(sink, 32)
        var total = 0
        for (sb in sink) {
            sb.append('x')
            total += sb.length
        }
        pool.recycleAll(sink)
        bh.consume(total)
    }
}
