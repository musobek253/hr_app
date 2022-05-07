package uz.hp.hrapp.repository;

import uz.hp.hrapp.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company, Integer> {
//    Optional<User> findByEmail(String email);
}
