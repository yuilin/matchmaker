package ru.yilin.matchmaker.app.service.match;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yilin.matchmaker.app.model.Queue;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class MatchInformServiceImpl implements MatchInformService {

    @Override
    public void inform(List<Queue> matchMembers, long counter) {
        List<Double> skills = matchMembers.stream().map(Queue::getSkill).collect(Collectors.toList());
        Double maxSkill = skills.get(0);
        Double minSkill = skills.get(0);
        Double overallSkill = skills.get(0);
        for (int i = 1; i < skills.size(); i++) {
            if (maxSkill < skills.get(i)) {
                maxSkill = skills.get(i);
            }
            if (minSkill > skills.get(i)) {
                minSkill = skills.get(i);
            }
            overallSkill = overallSkill + skills.get(i);
        }
        Double averageSkill = overallSkill / skills.size();

        List<Double> latencies = matchMembers.stream().map(Queue::getLatency).collect(Collectors.toList());
        Double maxLatency = latencies.get(0);
        Double minLatency = latencies.get(0);
        Double overallLatency = latencies.get(0);
        for (int i = 1; i < latencies.size(); i++) {
            if (maxLatency < latencies.get(i)) {
                maxLatency = latencies.get(i);
            }
            if (minLatency > latencies.get(i)) {
                minLatency = latencies.get(i);
            }
            overallLatency = overallLatency + latencies.get(i);
        }
        Double averageLatency = overallLatency / latencies.size();

        LocalDateTime now = LocalDateTime.now();
        List<Long> time = matchMembers.stream().map(queue -> ChronoUnit.SECONDS.between(queue.getEntranceTime(), now)).collect(Collectors.toList());
        Long maxTime = time.get(0);
        Long minTime = time.get(0);
        Double overall = time.get(0).doubleValue();
        for (int i = 1; i < time.size(); i++) {
            if (maxTime < time.get(i)) {
                maxTime = time.get(i);
            }
            if (minTime > time.get(i)) {
                minTime = time.get(i);
            }
            overall = overall + time.get(i);
        }
        Double averageTime = overall / time.size();


        log.info("Match {} starts!", counter);
        log.info("Names: {}", Arrays.toString(matchMembers.stream().map(Queue::getName).toArray()));
        log.info("Max skill: {}", maxSkill);
        log.info("Min skill: {}", minSkill);
        log.info("Average skill: {}", averageSkill);
        log.info("Max latency: {}", maxLatency);
        log.info("Min latency: {}", minLatency);
        log.info("Average latency: {}", averageLatency);
        log.info("Max time: {}", maxTime);
        log.info("Min time: {}", minTime);
        log.info("Average time: {}", averageTime);
    }
}
