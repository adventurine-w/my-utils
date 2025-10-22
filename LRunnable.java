package org.adv.clickerflex.ultimate_utils;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.IntConsumer;

@FunctionalInterface
public interface LRunnable extends IntConsumer, Runnable {
    @Override
    void accept(int iteration);

    default LoopingRunnable loop(int times) {
        AtomicInteger counter = new AtomicInteger(0);
        final LRunnable[] doneCallback = new LRunnable[1];
        return new LoopingRunnable(times, this, counter, doneCallback);
    }

    default void run() {
        this.accept(1);
    }
    default void runLater(long ticks) {
        Common.runTaskLater(()-> {
            this.accept(1);
        }, ticks);
    }

    class LoopingRunnable implements LRunnable {
        private final int times;
        private final LRunnable delegate;
        private final AtomicInteger counter;
        private final LRunnable[] doneCallback;

        public LoopingRunnable(int times, LRunnable delegate, AtomicInteger counter, LRunnable[] doneCallback) {
            this.times = times;
            this.delegate = delegate;
            this.counter = counter;
            this.doneCallback = doneCallback;
        }

        @Override
        public void accept(int iteration) {
            int current = counter.incrementAndGet();
            if (current > times) {
                if (doneCallback[0] != null) doneCallback[0].run();
                return;
            }
            delegate.accept(current);
        }

        @Override
        public void run() {
            accept(1);
        }

        public LoopingRunnable whenDone(Runnable callback) {
            doneCallback[0] = (n) -> callback.run();
            return this;
        }
    }
}
