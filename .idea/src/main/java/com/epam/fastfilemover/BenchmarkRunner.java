package com.epam.fastfilemover;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class BenchmarkRunner {
    public static void main(String[] args) throws IOException {
        org.openjdk.jmh.Main.main(args);
    }

    @State(Scope.Benchmark)
    public static class BenchmarkState {
        @Param({"c:/1/lindgren-dzieci-z-bullerbyn.pdf"})
        public String from;

        @Param({"c:/2/lindgren-dzieci-z-bullerbyn.pdf"})
        public String to;
    }


    @Benchmark
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    @BenchmarkMode(Mode.AverageTime)
    @Fork(value = 1)
    @Warmup(iterations = 5, timeUnit = TimeUnit.MILLISECONDS, time = 5000)
    @Measurement(iterations = 5, timeUnit = TimeUnit.MILLISECONDS, time = 5000)
    public boolean moveWithFileStreamsTest(BenchmarkState state) {
        return FastFileMover.moveWithFileStreams(state.from, state.from);
    }

    @Benchmark
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    @BenchmarkMode(Mode.AverageTime)
    @Fork(value = 1)
    @Warmup(iterations = 5, timeUnit = TimeUnit.MILLISECONDS, time = 5000)
    @Measurement(iterations = 5, timeUnit = TimeUnit.MILLISECONDS, time = 5000)
    public boolean moveWithFileStreamsWithBufferTest(BenchmarkState state) {
        return FastFileMover.moveWithFileStreamsWithBuffer(state.from, state.from);
    }

    @Benchmark
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    @BenchmarkMode(Mode.AverageTime)
    @Fork(value = 1)
    @Warmup(iterations = 5, timeUnit = TimeUnit.MILLISECONDS, time = 5000)
    @Measurement(iterations = 5, timeUnit = TimeUnit.MILLISECONDS, time = 5000)
    public boolean moveWithNioTest(BenchmarkState state) {
        return FastFileMover.moveWithNio(state.from, state.from);
    }

    @Benchmark
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    @BenchmarkMode(Mode.AverageTime)
    @Fork(value = 1)
    @Warmup(iterations = 5, timeUnit = TimeUnit.MILLISECONDS, time = 5000)
    @Measurement(iterations = 5, timeUnit = TimeUnit.MILLISECONDS, time = 5000)
    public boolean moveWithFileChannelTest(BenchmarkState state) {
        return FastFileMover.moveWithFileChannel(state.from, state.from);
    }


}
