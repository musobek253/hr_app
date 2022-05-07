package uz.hp.hrapp.repository;

import uz.hp.hrapp.entity.Turniket;
import uz.hp.hrapp.entity.TurniketHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

public interface TurniketHistoryRepository extends JpaRepository<TurniketHistory, UUID> {
//    Optional<User> findByEmail(String email);
//        List<TurniketHistory> findAllByTurniket(Turniket turniket);
        List<TurniketHistory> findAllByTurniketAndTimeIsBetween(Turniket turniket, Timestamp time, Timestamp time2);
}
