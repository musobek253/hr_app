package uz.hp.hrapp.component;

import uz.hp.hrapp.entity.Company;
import uz.hp.hrapp.entity.Role;
import uz.hp.hrapp.entity.User;
import uz.hp.hrapp.repository.CompanyRepository;
import uz.hp.hrapp.repository.RoleRepository;
import uz.hp.hrapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class DataLoader implements CommandLineRunner {

    @Value("${spring.datasource.initialization-mode}")
    private String initialMode;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    CompanyRepository companyRepository;

    @Override
    public void run(String... args) throws Exception {
        if (initialMode.equals("always")) {

            Set<Role> roles = new HashSet<>(roleRepository.findAll()); //bazada necha xil rol bo'lsa berdm


            User user = new User("direktor", passwordEncoder.encode("1234"),
                    "musobek253@gmail.com", roles, "direktor", true);
            User direktor = userRepository.save(user);

            Company company = new Company("Megadream", direktor);
            companyRepository.save(company);
        }
         }
}
