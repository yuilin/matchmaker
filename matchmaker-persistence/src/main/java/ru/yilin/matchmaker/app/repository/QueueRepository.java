package ru.yilin.matchmaker.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.yilin.matchmaker.app.persistence.QueuePO;

@Repository
public interface QueueRepository extends JpaRepository<QueuePO, Long> {
}
