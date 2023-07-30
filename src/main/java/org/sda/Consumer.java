package org.sda;

import java.util.concurrent.BlockingQueue;

public class Consumer implements Runnable {
    private final BlockingQueue<String> queue;
    private final RateLimiter rateLimiter;

    public Consumer(BlockingQueue<String> queue, RateLimiter rateLimiter) {
        this.queue = queue;
        this.rateLimiter = rateLimiter;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                String message = queue.take();
                if (rateLimiter.acquire()) {
                    System.out.println(message);
                } else {
                    //отбрасываем сообщения сверх лимита
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
