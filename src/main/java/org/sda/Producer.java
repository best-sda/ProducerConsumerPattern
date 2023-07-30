package org.sda;

import java.time.LocalTime;
import java.util.UUID;
import java.util.concurrent.BlockingQueue;

public class Producer implements Runnable {
    private final BlockingQueue<String> queue;
    private RateLimiter rateLimiter;

    public Producer(BlockingQueue<String> queue, RateLimiter rateLimiter) {
        this.queue = queue;
        this.rateLimiter = rateLimiter;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
                try {
                    if (rateLimiter.acquire()) {
                        String message = UUID.randomUUID() + " " + LocalTime.now().toString();
                        queue.put(message);
                    } else {
                        //отбрасываем сообщения сверх лимита
                    }

                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
        }
    }
}
