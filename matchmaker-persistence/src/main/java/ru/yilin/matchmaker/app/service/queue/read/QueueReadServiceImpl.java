package ru.yilin.matchmaker.app.service.queue.read;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.yilin.matchmaker.app.mapper.QueuePOMapper;
import ru.yilin.matchmaker.app.model.Queue;
import ru.yilin.matchmaker.app.repository.QueueRepository;

import java.util.List;

@Service
@Transactional(propagation = Propagation.REQUIRES_NEW)
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class QueueReadServiceImpl implements QueueReadService {

    QueueRepository repository;
    QueuePOMapper mapper;

    @Override
    public List<Queue> readAll() {
        return mapper.map(repository.findAll());
    }
}
