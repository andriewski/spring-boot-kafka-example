package by.mark.kafkaexample;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class KafkaExampleApplicationTests {

    @Test
    void contextLoads() {
    }


    @Test
    public void testLogback() throws Exception {
        Logger log = LoggerFactory.getLogger(KafkaExampleApplicationTests.class);
        // Для прямой ссылки java выкинет
        // java.lang.IllegalArgumentException: Self-suppression not permitted
        //ex.addSuppressed(ex);
        // поэтому ссылаемся транзитивно
        Exception ex = new Exception("Test exception");
        Exception ex1 = new Exception("Test exception1");
        ex.addSuppressed(ex1);
        ex1.addSuppressed(ex);
        log.error("Exception", ex);
    }
}
