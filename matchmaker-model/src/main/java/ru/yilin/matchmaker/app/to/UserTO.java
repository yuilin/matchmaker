package ru.yilin.matchmaker.app.to;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserTO {

    @NonNull
    String name;
    @NonNull
    Double skill;
    @NonNull
    Double latency;
}
