package ru.yilin.matchmaker.app.mapper;

import org.springframework.stereotype.Component;
import ru.yilin.matchmaker.app.model.Queue;
import ru.yilin.matchmaker.app.to.UserTO;

@Component
public class QueueMapper {

    public Queue map(UserTO userTO) {
        Queue queue = new Queue();
        queue.setName(userTO.getName());
        queue.setSkill(userTO.getSkill());
        queue.setLatency(userTO.getLatency());
        return queue;
    }

    public UserTO map(Queue queue) {
        UserTO userTO = new UserTO();
        userTO.setName(queue.getName());
        userTO.setSkill(queue.getSkill());
        userTO.setLatency(queue.getLatency());
        return userTO;
    }
}
