package uz.hp.hrapp.repository;

import uz.hp.hrapp.entity.Turniket;
import uz.hp.hrapp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface TurniketRepository extends JpaRepository<Turniket, UUID> {
    //    Optional<Turniket> findByEmail(String email);
    Optional<Turniket> findAllByOwner(User owner);

    Optional<Turniket> findByNumber(String number);

}
