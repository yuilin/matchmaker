package ru.yilin.matchmaker.app.mapper;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.yilin.matchmaker.app.model.Queue;
import ru.yilin.matchmaker.app.persistence.QueuePO;
import ru.yilin.matchmaker.app.util.RandomQueue;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = QueuePOMapperTest.MapperTestConfig.class)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class QueuePOMapperTest {

    Queue initialQueue;

    @Autowired
    QueuePOMapper mapper;

    @BeforeEach
    void before() {
        initialQueue = RandomQueue.randomQueue();
    }

    @Test
    void roundTripTest() {
        QueuePO queuePO = mapper.map(initialQueue);
        Queue actualQueue = mapper.map(queuePO);

        assertEquals(initialQueue, actualQueue);
    }

    @Configuration
    @ComponentScan("ru.yilin.matchmaker.app.mapper")
    protected static class MapperTestConfig {

    }
}
