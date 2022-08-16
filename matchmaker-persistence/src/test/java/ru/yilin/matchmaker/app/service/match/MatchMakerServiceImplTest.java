package ru.yilin.matchmaker.app.service.match;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.yilin.matchmaker.app.config.MatchMakerProperties;
import ru.yilin.matchmaker.app.model.Queue;
import ru.yilin.matchmaker.app.service.queue.read.QueueReadService;
import ru.yilin.matchmaker.app.service.queue.write.QueueRemoveService;
import ru.yilin.matchmaker.app.util.RandomQueue;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MatchMakerServiceImplTest {

    @InjectMocks
    MatchMakerServiceImpl matchMakerService;

    @Mock
    QueueReadService queueReadService;
    @Mock
    MatchMakerProperties properties;
    @Mock
    QueueRemoveService queueRemoveService;
    @Mock
    MatchInformService informService;

    final Integer groupSize = 3;
    final long counter = 1;

    Queue queue1;
    Queue queue2;
    Queue queue3;

    @BeforeEach
    void before() {
        queue1 = RandomQueue.randomQueue();
        queue2 = RandomQueue.randomQueue();
        queue3 = RandomQueue.randomQueue();

    }

    @Test
    void checkQueue_matchIsNotCreatedWhenNotEnoughPlayers() {
        List<Queue> queueList = List.of(queue1);

        doReturn(queueList).when(queueReadService).readAll();
        doReturn(groupSize).when(properties).getGroupSize();

        matchMakerService.checkQueue();

        verify(informService, never()).inform(queueList, counter);
        verify(queueRemoveService, never()).remove(any());
    }

    @Test
    void checkQueue_matchIsCreatedWhenEnoughPlayers() {
        List<Queue> queueList = List.of(queue1, queue2, queue3);
        Double skillError = 20.0;
        Double latencyError = 200.0;
        Double timeSpentLimit = 1.0;

        doReturn(queueList).when(queueReadService).readAll();
        doReturn(groupSize).when(properties).getGroupSize();
        doReturn(skillError).when(properties).getSkillError();
        doReturn(latencyError).when(properties).getLatencyError();
        doReturn(timeSpentLimit).when(properties).getTimeSpentLimit();

        matchMakerService.checkQueue();

        verify(informService, times(1)).inform(queueList, counter);
        verify(queueRemoveService, times(1)).remove(queue1);
        verify(queueRemoveService, times(1)).remove(queue2);
        verify(queueRemoveService, times(1)).remove(queue3);
    }
}
