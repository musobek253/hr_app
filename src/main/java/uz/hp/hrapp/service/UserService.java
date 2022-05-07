package uz.hp.hrapp.service;

import uz.hp.hrapp.component.Checker;
import uz.hp.hrapp.component.MailSender;
import uz.hp.hrapp.component.PasswordGenerator;
import uz.hp.hrapp.entity.Role;
import uz.hp.hrapp.entity.User;
import uz.hp.hrapp.entity.enums.RoleName;
import uz.hp.hrapp.payload.ApiResponse;
import uz.hp.hrapp.payload.UserDto;
import uz.hp.hrapp.repository.RoleRepository;
import uz.hp.hrapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    RoleRepository roleRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    Checker checker;
    @Autowired
    PasswordGenerator passwordGenerator;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    MailSender mailSender;
    @Autowired
    TurniketService turniketService;

    public ApiResponse add(UserDto userDto) throws MessagingException {
        Optional<Role> optionalRole = roleRepository.findById(userDto.getRoleId());
        if (!optionalRole.isPresent()) return new ApiResponse("Role id not found!", false);

        boolean check = checker.check(optionalRole.get().getName().name());//

        if (!check) {
            return new ApiResponse("Dostup netu!", false);
        }

        if (userRepository.existsByEmail(userDto.getEmail())) {
            return new ApiResponse("Already exists!", false);
        }
        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setEmail(userDto.getEmail());
        user.setPosition(userDto.getPosition());

        Set<Role> roleSet = new HashSet<>();
        roleSet.add(optionalRole.get());
        user.setRoles(roleSet);

        String password = passwordGenerator.genRandomPassword(8);
        user.setPassword(passwordEncoder.encode(password));

        String code = UUID.randomUUID().toString();
        user.setVerifyCode(code);

        User save = userRepository.save(user);
        turniketService.add(1,save.getId());
        //mail xabar yuborish kk
        boolean addStaff = mailSender.mailTextAddStaff(userDto.getEmail(), code, password);

        if (addStaff) {
            return new ApiResponse("User qo'shildi! va emailga xabar ketdi!", true);
        } else {
            return new ApiResponse("Xatolik yuz berdi", false);
        }
    }
    public ApiResponse getByEmail(String email){
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (!userOptional.isPresent())
            return new ApiResponse("Email not found!", false);

        Set<Role> roles = userOptional.get().getRoles();
        String role = RoleName.ROLE_STAFF.name();
        for (Role roleName : roles) {
            role = roleName.getName().name();
            break;
        }

        boolean check = checker.check(role);
        if (!check)
            return new ApiResponse("You have no such right!", false);

        return new ApiResponse("Get by email!",true,userOptional.get());
    }
}
