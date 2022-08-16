package ru.yilin.matchmaker.app.service.match;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.yilin.matchmaker.app.config.MatchMakerProperties;
import ru.yilin.matchmaker.app.model.Queue;
import ru.yilin.matchmaker.app.service.queue.read.QueueReadService;
import ru.yilin.matchmaker.app.service.queue.write.QueueRemoveService;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Transactional(propagation = Propagation.REQUIRES_NEW)
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MatchMakerServiceImpl implements MatchMakerService {

    QueueReadService queueReadService;
    QueueRemoveService queueRemoveService;
    MatchInformService informService;
    MatchMakerProperties properties;

    AtomicLong matchCounter = new AtomicLong();

    @Override
    public void checkQueue() {
        List<Queue> queues = queueReadService.readAll();
        if (queues.size() >= properties.getGroupSize()) {
            List<Queue> matchMembers = findCandidates(queues);
            if (!matchMembers.isEmpty()) {
                createMatch(matchMembers);
            }
        }
    }

    private void createMatch(List<Queue> matchMembers) {
        informService.inform(matchMembers, matchCounter.incrementAndGet());
        matchMembers.forEach(queueRemoveService::remove);
    }

    private List<Queue> findCandidates(List<Queue> queues) {
        LocalDateTime now = LocalDateTime.now();
        List<Queue> sadCandidates = queues.stream()
                                          .filter(queue -> ChronoUnit.SECONDS.between(queue.getEntranceTime(), now) >= properties.getTimeSpentLimit())
                                          .collect(Collectors.toList());
        for (Queue sadCandidate : sadCandidates) {
            long count = filterQueue(queues, sadCandidate).count();
            if (count >= properties.getGroupSize()) {
                return getMatchMembers(queues, sadCandidate);
            } else {
                count = filterQueue(queues, sadCandidate, properties.getTimeSpentMultiplier()).count();
            }
            if (count >= properties.getGroupSize()) {
                return getMatchMembers(queues, sadCandidate, properties.getTimeSpentMultiplier());
            }
        }
        List<Queue> candidates = queues.stream()
                                       .filter(queue -> ChronoUnit.SECONDS.between(queue.getEntranceTime(), now) < properties.getTimeSpentLimit())
                                       .collect(Collectors.toList());
        for (Queue candidate : candidates) {
            long count = filterQueue(queues, candidate).count();
            if (count >= properties.getGroupSize()) {
                return getMatchMembers(queues, candidate);
            }
        }
        return List.of();
    }

    private List<Queue> getMatchMembers(List<Queue> candidates, Queue candidate) {
        return filterQueue(candidates, candidate).collect(Collectors.toList()).subList(0, properties.getGroupSize());
    }

    private List<Queue> getMatchMembers(List<Queue> candidates, Queue candidate, Integer multiplier) {
        return filterQueue(candidates, candidate, multiplier).collect(Collectors.toList()).subList(0, properties.getGroupSize());
    }

    private Stream<Queue> filterQueue(List<Queue> candidates, Queue candidate, Integer multiplier) {
        return candidates.stream()
                         .filter(q -> Math.abs(q.getSkill() - candidate.getSkill()) <= properties.getSkillError() * multiplier && Math.abs(q.getLatency() - candidate
                                 .getLatency() * multiplier) <= properties.getLatencyError());
    }

    private Stream<Queue> filterQueue(List<Queue> candidates, Queue candidate) {
        return filterQueue(candidates, candidate, 1);
    }
}
