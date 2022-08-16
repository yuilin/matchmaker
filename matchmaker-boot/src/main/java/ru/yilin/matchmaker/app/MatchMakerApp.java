package ru.yilin.matchmaker.app;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan
@Slf4j
public class MatchMakerApp {

    public static void main(final String[] args) {
        log.info("Starting MatchMakerApp ...");
        SpringApplication.run(MatchMakerApp.class, args);
    }
}
