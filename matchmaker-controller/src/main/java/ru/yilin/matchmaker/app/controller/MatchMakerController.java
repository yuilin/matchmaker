package ru.yilin.matchmaker.app.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.yilin.matchmaker.app.mapper.QueueMapper;
import ru.yilin.matchmaker.app.model.Queue;
import ru.yilin.matchmaker.app.service.match.MatchMakerService;
import ru.yilin.matchmaker.app.service.queue.write.QueueAddService;
import ru.yilin.matchmaker.app.to.UserTO;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MatchMakerController {

    QueueAddService queueAddService;
    MatchMakerService matchMakerService;

    QueueMapper queueMapper;

    @PostMapping("/users")
    public void addUserToQueue(@RequestBody UserTO user) {
        Queue queue = queueMapper.map(user);
        queueAddService.add(queue);
        matchMakerService.checkQueue();
    }
}
