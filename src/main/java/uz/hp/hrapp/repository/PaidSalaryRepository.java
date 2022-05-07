package uz.hp.hrapp.repository;

import uz.hp.hrapp.entity.PaidSalary;
import uz.hp.hrapp.entity.User;
import uz.hp.hrapp.entity.enums.Month;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PaidSalaryRepository extends JpaRepository<PaidSalary, UUID> {
    Optional<PaidSalary> findByOwnerAndPeriod(User owner, Month period);
    List<PaidSalary> findAllByOwner(User user);
    List<PaidSalary> findAllByPeriod(Month period);

}
