package ru.yilin.matchmaker.app.service.match;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.LoggerFactory;
import ru.yilin.matchmaker.app.model.Queue;
import ru.yilin.matchmaker.app.util.LogAppender;
import ru.yilin.matchmaker.app.util.RandomQueue;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static ch.qos.logback.classic.Level.INFO;
import static ru.yilin.matchmaker.app.util.LogAppender.assertTrueLogEvent;

@ExtendWith(MockitoExtension.class)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MatchInformServiceImplTest {

    @InjectMocks
    MatchInformServiceImpl matchInformService;

    Queue queue1;
    Queue queue2;
    Queue queue3;

    @BeforeEach
    void before() {
        queue1 = RandomQueue.randomQueue();
        queue2 = RandomQueue.randomQueue();
        queue3 = RandomQueue.randomQueue();

        Logger logger = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
        LogAppender logAppender = new LogAppender();
        logAppender.setContext((LoggerContext) LoggerFactory.getILoggerFactory());
        logger.setLevel(INFO);
        logger.addAppender(logAppender);
        logAppender.start();
    }

    @Test
    void inform() {
        int counter = 1;
        List<Queue> queueList = List.of(queue1, queue2, queue3);

        matchInformService.inform(queueList, counter);

        assertTrueLogEvent(INFO, "Match " + counter + " starts!");
        assertTrueLogEvent(INFO, "Names: [" + queue1.getName() + ", " + queue2.getName() + ", " + queue3.getName() + "]");
        assertTrueLogEvent(INFO, "Max skill: " + queueList.stream().map(Queue::getSkill).mapToDouble(v -> v).max().getAsDouble());
        assertTrueLogEvent(INFO, "Min skill: " + queueList.stream().map(Queue::getSkill).mapToDouble(v -> v).min().getAsDouble());
        assertTrueLogEvent(INFO, "Average skill: " + queueList.stream().map(Queue::getSkill).mapToDouble(v -> v).average().getAsDouble());
        assertTrueLogEvent(INFO, "Max latency: " + queueList.stream().map(Queue::getLatency).mapToDouble(v -> v).max().getAsDouble());
        assertTrueLogEvent(INFO, "Min latency: " + queueList.stream().map(Queue::getLatency).mapToDouble(v -> v).min().getAsDouble());
        assertTrueLogEvent(INFO, "Average latency: " + queueList.stream().map(Queue::getLatency).mapToDouble(v -> v).average().getAsDouble());
        LocalDateTime now = LocalDateTime.now();
        assertTrueLogEvent(INFO,
                           "Max time: " + queueList.stream()
                                                   .map(queue -> ChronoUnit.SECONDS.between(queue.getEntranceTime(), now))
                                                   .mapToLong(v -> v)
                                                   .max()
                                                   .getAsLong());
        assertTrueLogEvent(INFO,
                           "Min time: " + queueList.stream()
                                                   .map(queue -> ChronoUnit.SECONDS.between(queue.getEntranceTime(), now))
                                                   .mapToLong(v -> v)
                                                   .min()
                                                   .getAsLong());
        assertTrueLogEvent(INFO,
                           "Average time: " + queueList.stream()
                                                       .map(queue -> ChronoUnit.SECONDS.between(queue.getEntranceTime(), now))
                                                       .mapToLong(v -> v)
                                                       .average()
                                                       .getAsDouble());
    }
}
