package org.sda;

import java.time.Clock;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Main {
    //Длина очереди
    private static final int QUEUE_SIZE = 100;
    //Количество сообщений за период
    private static final int RATE_LIMIT_PRODUCER = 5;
    private static final int RATE_LIMIT_CONSUMER = 1;
    //Период в мс
    private static final int RATE_PERIOD = 1000;
    //Время работы в мс
    private static final int WORK_TIME = 60 * 1000;
    public static void main(String[] args) throws InterruptedException {
        BlockingQueue<String> queue = new LinkedBlockingQueue<>(QUEUE_SIZE);
        Clock clock = Clock.systemUTC();
        RateLimiter rateLimiterProducer = new RateLimiter(RATE_LIMIT_PRODUCER, RATE_PERIOD, clock);
        RateLimiter rateLimiterConsumer = new RateLimiter(RATE_LIMIT_CONSUMER, RATE_PERIOD, clock);
        Thread producerThread = new Thread(new Producer(queue, rateLimiterProducer));
        Thread consumerThread = new Thread(new Consumer(queue, rateLimiterConsumer));
        producerThread.start();
        consumerThread.start();
        Thread.sleep(WORK_TIME);
        producerThread.interrupt();
        consumerThread.interrupt();
    }

}