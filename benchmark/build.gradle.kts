plugins {
    kotlin("jvm")
    id("me.champeau.jmh") version "0.7.2"
}

dependencies {
    implementation(project(":fastutil4k-extensions-only"))
    implementation(project(":fastutil4k-more-collections"))
    implementation(libs.fastutil)

    jmh(project(":fastutil4k-extensions-only"))
    jmh(project(":fastutil4k-more-collections"))
    jmh(libs.fastutil)
}

jmh {
    warmupIterations = (findProperty("jmh.warmupIterations") as String?)?.toInt() ?: 2
    iterations = (findProperty("jmh.iterations") as String?)?.toInt() ?: 3
    fork = (findProperty("jmh.fork") as String?)?.toInt() ?: 1
    resultFormat = "JSON"
    resultsFile.set(layout.buildDirectory.file("reports/jmh/results.json"))
    if (project.hasProperty("jmh.includes")) {
        includes = listOf(project.property("jmh.includes").toString())
    }
}

// Keep benchmark execution explicit: `:benchmark:jmh`
tasks.named("build") {
    enabled = false
}
tasks.named("check") {
    enabled = false
}
tasks.named("test") {
    enabled = false
}
