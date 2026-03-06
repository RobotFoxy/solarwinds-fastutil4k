# benchmark

JMH benchmark module for `fastutil4k`.

This module is designed for local/manual performance evaluation:

- It is independent from publish artifacts.
- It is not part of the default `build/check/test` lifecycle.

## Included benchmarks (first batch)

- `PoolBenchmark`
- `LfuCacheBenchmark`
- `WeightedSortedListBenchmark`
- `RangesBenchmark`
- `MapTransformBenchmark`

`MapTransformBenchmark` compares:

- `mapToArray { ... }.asList()`
- `collection.map { ... }`
- `sequence.map { ... }.toList()`
- `stream.map { ... }.toList()`

## Run

```bash
# Compile benchmark source set
./gradlew :benchmark:compileJmhKotlin

# Smoke run with short params
./gradlew :benchmark:jmh --no-configuration-cache \
  -Pjmh.includes=MapTransformBenchmark \
  -Pjmh.warmupIterations=1 \
  -Pjmh.iterations=1 \
  -Pjmh.fork=1

# Full suite
./gradlew :benchmark:jmh --no-configuration-cache
```

## Parameters

Optional command-line properties:

- `-Pjmh.includes=<regex>`
- `-Pjmh.warmupIterations=<int>`
- `-Pjmh.iterations=<int>`
- `-Pjmh.fork=<int>`

## Output

- Console report from JMH
- JSON report: `build/reports/jmh/results.json`

## Note

Current JMH plugin setup may conflict with Gradle configuration cache.
Use `--no-configuration-cache` when running `:benchmark:jmh`.
