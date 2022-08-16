package ru.yilin.matchmaker.app.mapper;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.yilin.matchmaker.app.model.Queue;
import ru.yilin.matchmaker.app.to.UserTO;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = QueueMapperTest.MapperTestConfig.class)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class QueueMapperTest {

    UserTO initialUser;

    @Autowired
    QueueMapper mapper;

    @BeforeEach
    void before() {
        initialUser = new UserTO();
        initialUser.setName("User");
        initialUser.setSkill(10.0);
        initialUser.setLatency(30.2);
    }

    @Test
    void roundTripTest() {
        Queue queue = mapper.map(initialUser);
        UserTO actualUser = mapper.map(queue);

        assertEquals(initialUser, actualUser);
    }

    @Configuration
    @ComponentScan("ru.yilin.matchmaker.app.mapper")
    protected static class MapperTestConfig {

    }
}