package ru.yilin.matchmaker.app.persistence;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "QUEUE")
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class QueuePO {

    @Id
    @GeneratedValue
    Long id;

    @Column
    String name;

    @Column
    Double skill;

    @Column
    Double latency;

    @Column
    LocalDateTime entranceTime;
}
