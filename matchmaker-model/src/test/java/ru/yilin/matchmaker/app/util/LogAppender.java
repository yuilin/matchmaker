package ru.yilin.matchmaker.app.util;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.AppenderBase;
import org.junit.jupiter.api.Assertions;

import java.util.ArrayList;
import java.util.List;

public class LogAppender extends AppenderBase<ILoggingEvent> {

    private static final List<ILoggingEvent> LOG_EVENTS = new ArrayList();

    public LogAppender() {
    }

    protected void append(ILoggingEvent loggingEvent) {
        LOG_EVENTS.add(loggingEvent);
    }

    public static List<ILoggingEvent> getLogEvents() {
        return LOG_EVENTS;
    }

    private static boolean assertLogEvent(Level expectedLevel, String expectedMessage) {
        return getLogEvents().stream().anyMatch((event) -> {
            return event.getLevel().equals(expectedLevel) && event.getFormattedMessage().contains(expectedMessage);
        });
    }

    public static void assertTrueLogEvent(Level expectedLevel, String expectedMessage) {
        Assertions.assertTrue(assertLogEvent(expectedLevel, expectedMessage),
                              String.format("Expected %s message '%s' was not found in logs!", expectedLevel.levelStr, expectedMessage));
    }
}
