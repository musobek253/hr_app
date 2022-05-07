package uz.hp.hrapp.repository;

import uz.hp.hrapp.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Integer> {
//    Role findByName(RoleName name);
}
