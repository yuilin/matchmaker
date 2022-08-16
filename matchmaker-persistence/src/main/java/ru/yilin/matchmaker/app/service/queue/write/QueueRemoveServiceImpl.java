package ru.yilin.matchmaker.app.service.queue.write;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.yilin.matchmaker.app.mapper.QueuePOMapper;
import ru.yilin.matchmaker.app.model.Queue;
import ru.yilin.matchmaker.app.persistence.QueuePO;
import ru.yilin.matchmaker.app.repository.QueueRepository;

@Service
@Transactional(propagation = Propagation.REQUIRES_NEW)
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class QueueRemoveServiceImpl implements QueueRemoveService {

    QueueRepository repository;
    QueuePOMapper mapper;

    @Override
    public void remove(Queue queue) {
        QueuePO queuePO = mapper.map(queue);
        repository.delete(queuePO);
        log.info("Player {} removed from queue", queue.getName());
    }
}
