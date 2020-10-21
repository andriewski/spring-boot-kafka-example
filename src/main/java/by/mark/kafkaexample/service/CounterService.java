package by.mark.kafkaexample.service;

import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicInteger;

@Service
public class CounterService {

    private final AtomicInteger atomicInteger = new AtomicInteger();

    public void increment() {
        atomicInteger.incrementAndGet();
    }

    public int getResult() {
        return atomicInteger.get();
    }
}
