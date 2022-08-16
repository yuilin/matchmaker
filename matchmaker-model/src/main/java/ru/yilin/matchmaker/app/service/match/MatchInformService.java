package ru.yilin.matchmaker.app.service.match;

import ru.yilin.matchmaker.app.model.Queue;

import java.util.List;

public interface MatchInformService {

    void inform(List<Queue> matchMembers, long counter);
}
