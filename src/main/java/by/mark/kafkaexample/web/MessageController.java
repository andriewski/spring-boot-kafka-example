package by.mark.kafkaexample.web;

import by.mark.kafkaexample.config.KafkaTopics;
import by.mark.kafkaexample.model.User;
import by.mark.kafkaexample.service.CounterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping(MessageController.PATH)
@RequiredArgsConstructor
public class MessageController {

    public static final String
            PATH = "/message",
            SEND_RANDOM = "/send-random-message",
            RESULT = "/count-result";
    private final CounterService counterService;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    @ResponseStatus(HttpStatus.CREATED)
    @GetMapping(SEND_RANDOM)
    public void sendRandomMessage() {
        kafkaTemplate.send(KafkaTopics.DEMO_TOPIC, UUID.randomUUID().toString(), new User("Petr", 42));
    }

    @GetMapping(RESULT)
    public Integer getCounterResult() {
        return counterService.getResult();
    }
}
