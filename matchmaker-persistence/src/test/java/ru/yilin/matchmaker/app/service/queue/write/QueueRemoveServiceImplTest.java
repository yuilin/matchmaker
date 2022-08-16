package ru.yilin.matchmaker.app.service.queue.write;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.LoggerFactory;
import ru.yilin.matchmaker.app.mapper.QueuePOMapper;
import ru.yilin.matchmaker.app.model.Queue;
import ru.yilin.matchmaker.app.persistence.QueuePO;
import ru.yilin.matchmaker.app.repository.QueueRepository;
import ru.yilin.matchmaker.app.util.LogAppender;
import ru.yilin.matchmaker.app.util.RandomQueue;

import static ch.qos.logback.classic.Level.INFO;
import static org.mockito.Mockito.when;
import static ru.yilin.matchmaker.app.util.LogAppender.assertTrueLogEvent;

@ExtendWith(MockitoExtension.class)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class QueueRemoveServiceImplTest {

    @InjectMocks
    QueueRemoveServiceImpl removeService;

    @Mock
    QueueRepository repository;

    @Mock
    QueuePOMapper mapper;

    QueuePO queuePO;
    Queue queue;

    @BeforeEach
    void before() {
        queue = RandomQueue.randomQueue();
        queuePO = new QueuePO();
        queuePO.setId(1L);

        Logger logger = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
        LogAppender logAppender = new LogAppender();
        logAppender.setContext((LoggerContext) LoggerFactory.getILoggerFactory());
        logger.setLevel(INFO);
        logger.addAppender(logAppender);
        logAppender.start();

    }

    @Test
    void removeFromQueue() {
        when(mapper.map(queue)).thenReturn(queuePO);

        removeService.remove(queue);

        assertTrueLogEvent(INFO, "Player " + queue.getName() + " removed from queue");
    }
}
