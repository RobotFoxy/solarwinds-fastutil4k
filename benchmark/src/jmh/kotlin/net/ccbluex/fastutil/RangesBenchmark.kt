package net.ccbluex.fastutil

import org.openjdk.jmh.annotations.Benchmark
import org.openjdk.jmh.annotations.BenchmarkMode
import org.openjdk.jmh.annotations.Mode
import org.openjdk.jmh.annotations.OutputTimeUnit
import org.openjdk.jmh.annotations.Param
import org.openjdk.jmh.annotations.Scope
import org.openjdk.jmh.annotations.State
import org.openjdk.jmh.infra.Blackhole
import java.util.concurrent.TimeUnit

@State(Scope.Thread)
@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
open class RangesBenchmark {
    @Param("1000", "100000", "1000000")
    var size: Int = 0

    @Benchmark
    fun doubleStepCreateAndIterate(bh: Blackhole) {
        val list = (0.0..size.toDouble()).step(1.0)
        var sum = 0.0
        for (i in 0 until list.size) {
            sum += list.getDouble(i)
        }
        bh.consume(sum)
    }

    @Benchmark
    fun floatStepCreateAndIterate(bh: Blackhole) {
        val list = (0f..size.toFloat()).step(1f)
        var sum = 0f
        for (i in 0 until list.size) {
            sum += list.getFloat(i)
        }
        bh.consume(sum)
    }
}
