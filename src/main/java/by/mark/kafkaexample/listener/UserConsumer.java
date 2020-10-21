package by.mark.kafkaexample.listener;

import by.mark.kafkaexample.config.KafkaTopics;
import by.mark.kafkaexample.model.User;
import by.mark.kafkaexample.service.CounterService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserConsumer {

    private final CounterService counterService;

    @KafkaListener(topics = KafkaTopics.DEMO_TOPIC, concurrency = "10")
    public void incrementMessage(ConsumerRecord<String, User> record) {
        log.info(String.valueOf(record.value()));
        counterService.increment();
    }
}
