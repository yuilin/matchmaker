package ru.yilin.matchmaker.app.util;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.RandomStringUtils;
import ru.yilin.matchmaker.app.model.Queue;

import java.time.LocalDateTime;
import java.util.Random;

@UtilityClass
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RandomQueue {

    Random RANDOM = new Random();

    public Queue randomQueue() {
        Queue queue = new Queue();
        queue.setId(Long.valueOf(RandomStringUtils.randomNumeric(3)));
        queue.setName(RandomStringUtils.randomAlphabetic(10));
        queue.setSkill(randomDouble(1.0, 20.0));
        queue.setLatency(randomDouble(1.0, 200.0));
        queue.setEntranceTime(LocalDateTime.now());
        return queue;
    }

    private Double randomDouble(double min, double max) {
        return min + (double) (RANDOM.nextInt((int) (max - min) * 10)) / 10;
    }

}
