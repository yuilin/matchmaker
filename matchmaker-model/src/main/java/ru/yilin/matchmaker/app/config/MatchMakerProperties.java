package ru.yilin.matchmaker.app.config;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("match")
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MatchMakerProperties {

    Integer groupSize;
    Double skillError;
    Double latencyError;
    Double timeSpentLimit;
    Integer timeSpentMultiplier;

}
