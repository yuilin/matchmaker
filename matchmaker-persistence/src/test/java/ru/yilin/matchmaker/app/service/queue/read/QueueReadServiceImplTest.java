package ru.yilin.matchmaker.app.service.queue.read;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.yilin.matchmaker.app.mapper.QueuePOMapper;
import ru.yilin.matchmaker.app.model.Queue;
import ru.yilin.matchmaker.app.persistence.QueuePO;
import ru.yilin.matchmaker.app.repository.QueueRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class QueueReadServiceImplTest {

    @InjectMocks
    QueueReadServiceImpl readService;

    @Mock
    QueueRepository repository;

    @Mock
    QueuePOMapper mapper;

    QueuePO queuePO;
    Queue queue;

    @BeforeEach
    void before() {
        queuePO = new QueuePO();
        queue = new Queue();
    }

    @Test
    void readAll_notEmpty() {
        doReturn(List.of(queuePO)).when(repository).findAll();
        doReturn(List.of(queue)).when(mapper).map(List.of(queuePO));

        List<Queue> result = readService.readAll();

        assertFalse(result.isEmpty());
    }

    @Test
    void readAll_empty() {
        doReturn(List.of()).when(repository).findAll();

        List<Queue> result = readService.readAll();

        assertTrue(result.isEmpty());
    }
}
