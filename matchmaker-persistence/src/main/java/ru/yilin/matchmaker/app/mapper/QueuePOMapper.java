package ru.yilin.matchmaker.app.mapper;

import org.springframework.stereotype.Component;
import ru.yilin.matchmaker.app.model.Queue;
import ru.yilin.matchmaker.app.persistence.QueuePO;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class QueuePOMapper {

    public Queue map(QueuePO queuePO) {
        Queue queue = new Queue();
        queue.setId(queuePO.getId());
        queue.setName(queuePO.getName());
        queue.setSkill(queuePO.getSkill());
        queue.setLatency(queuePO.getLatency());
        queue.setEntranceTime(queuePO.getEntranceTime());
        return queue;
    }

    public List<Queue> map(List<QueuePO> queuePOList) {
        return queuePOList.stream().map(this::map).collect(Collectors.toList());
    }

    public QueuePO map(Queue queue) {
        QueuePO queuePO = new QueuePO();
        queuePO.setId(queue.getId());
        queuePO.setName(queue.getName());
        queuePO.setSkill(queue.getSkill());
        queuePO.setLatency(queue.getLatency());
        queuePO.setEntranceTime(queue.getEntranceTime());
        return queuePO;
    }
}
