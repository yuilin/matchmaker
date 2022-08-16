package ru.yilin.matchmaker.app.model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Queue {

    Long id;
    String name;
    Double skill;
    Double latency;
    LocalDateTime entranceTime;

}
