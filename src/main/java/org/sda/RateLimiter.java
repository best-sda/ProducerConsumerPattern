package org.sda;

import java.time.Clock;

//Алгоритм ограничения пропускной способности Fixed Window (без сглаживания)
public class RateLimiter implements IRateLimiter {
    private final int maxPermits;
    private final long period;
    private long nextPermitTime;
    private int permits;

    private Clock clock;

    public RateLimiter(int maxPermits, long period, Clock clock) {
        this.maxPermits = maxPermits;
        this.period = period;
        this.nextPermitTime = clock.millis();
        this.permits = 0;
        this.clock = clock;
    }

    @Override
    public synchronized boolean acquire() {
        long now = clock.millis();
        if (now > nextPermitTime) {
            permits = 0;
            nextPermitTime = now + period;
        }

        if (permits < maxPermits) {
            permits++;
            return true;
        } else {
            return false;
        }
    }
}
