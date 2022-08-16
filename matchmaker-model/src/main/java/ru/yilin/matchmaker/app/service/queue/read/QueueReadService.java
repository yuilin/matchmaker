package ru.yilin.matchmaker.app.service.queue.read;

import ru.yilin.matchmaker.app.model.Queue;

import java.util.List;

public interface QueueReadService {

    List<Queue> readAll();
}
