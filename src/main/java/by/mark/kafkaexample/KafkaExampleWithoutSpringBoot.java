package by.mark.kafkaexample;

import by.mark.kafkaexample.config.KafkaTopics;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.boot.SpringApplication;

import java.io.Closeable;
import java.time.Duration;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

public class KafkaExampleWithoutSpringBoot {

    final static String HOST = "localhost:9092";

    public static void main(String[] args) throws InterruptedException {
        SpringApplication.run(KafkaExampleApplication.class, args);

        String topic = KafkaTopics.DEMO_TOPIC;

        new Thread(() -> {
            try (MyProducer myProducer = new MyProducer(topic)) {
                for (int i = 0; i < 100; i++) {
                    myProducer.send(String.valueOf(i), String.format("Hello from producer, %s", i));
                    TimeUnit.SECONDS.sleep(4);
                }
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }).start();

        getStart(new MyConsumer(topic));
        getStart(new MyConsumer(topic));
        getStart(new MyConsumer(topic));
        getStart(new MyConsumer(topic));
        getStart(new MyConsumer(topic));
        getStart(new MyConsumer(topic));
        getStart(new MyConsumer(topic));

        TimeUnit.MINUTES.sleep(2);
    }

    private static void getStart(MyConsumer consumer) {
        new Thread(() ->
                consumer.consume(record -> System.out.printf("Gor key: %s value: %s%n", record.key(), record.value()))
        ).start();
    }

    static class MyConsumer implements Closeable {

        private final String topic;
        private final KafkaConsumer<String, String> consumer;

        public MyConsumer(String topic) {
            this.topic = topic;
            this.consumer = getConsumer();
        }

        private KafkaConsumer<String, String> getConsumer() {
            Properties properties = new Properties();
            properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, HOST);
            properties.put(ConsumerConfig.GROUP_ID_CONFIG, "groupId");
            properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
            properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());

            KafkaConsumer<String, String> consumer = new KafkaConsumer<>(properties);
            consumer.subscribe(List.of(topic));

            return consumer;
        }

        public void consume(Consumer<ConsumerRecord<String, String>> recordConsumer) {
            try {
                Thread.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            while (true) {
                var records = consumer.poll(Duration.ofSeconds(1));
                records.forEach(recordConsumer);
            }
        }

        @Override
        public void close() {
            consumer.close();
        }
    }

    static class MyProducer implements Closeable {

        private final KafkaProducer<String, String> producer;
        private final String topic;

        public MyProducer(String topic) {
            this.topic = topic;
            this.producer = getProducer();
        }

        public void send(String key, String value) throws ExecutionException, InterruptedException {
            producer
                    .send(new ProducerRecord<>(topic, key, value))
                    .get();
        }

        @Override
        public void close() {
            producer.close();
        }

        private KafkaProducer<String, String> getProducer() {
            var props = new Properties();
            props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, HOST);
            props.put(ProducerConfig.CLIENT_ID_CONFIG, "clientId");
            props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
            props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

            return new KafkaProducer<>(props);
        }
    }
}
