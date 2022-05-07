package uz.hp.hrapp.service;

import uz.hp.hrapp.component.Checker;
import uz.hp.hrapp.entity.Role;
import uz.hp.hrapp.entity.Turniket;
import uz.hp.hrapp.entity.User;
import uz.hp.hrapp.entity.enums.RoleName;
import uz.hp.hrapp.payload.ApiResponse;
import uz.hp.hrapp.repository.CompanyRepository;
import uz.hp.hrapp.repository.TurniketHistoryRepository;
import uz.hp.hrapp.repository.TurniketRepository;
import uz.hp.hrapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;


@Service
public class TurniketService {
    @Autowired
    TurniketRepository turniketRepository;
    @Autowired
    TurniketHistoryRepository tuniketHistoryRepository;
    @Autowired
    CompanyRepository companyRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    Checker checker;
    public void add(Integer companyId, UUID userId){
        Turniket turniket=new Turniket();
        turniket.setCompany(companyRepository.getOne(companyId));
        turniket.setOwner(userRepository.getOne(userId));
        turniketRepository.save(turniket);
    }
    public ApiResponse getByUser(User user){
        Set<Role> roles = user.getRoles();
        String role = RoleName.ROLE_STAFF.name();
        for (Role roleName : roles) {
            role = roleName.getName().name();
            break;
        }
        boolean check = checker.check(role);
        if (!check)
            return new ApiResponse("Sizga mumkin emas!", false);

        Optional<Turniket> optionalTurniket = turniketRepository.findAllByOwner(user);
        return new ApiResponse("TurniketListByUser", true, optionalTurniket);
    }


    public ApiResponse delete(String number){
        Optional<Turniket> optionalTurniket = turniketRepository.findByNumber(number);
        if (!optionalTurniket.isPresent())
            return new ApiResponse("Turniket not found!", false);


        Set<Role> roles = optionalTurniket.get().getOwner().getRoles();
        String role = null;
        for (Role roleName : roles) {
            role = roleName.getName().name();
            break;
        }
        boolean check = checker.check(role);

        if (!check)
            return new ApiResponse("You have no such right!", false);

        turniketRepository.delete(optionalTurniket.get());
        return new ApiResponse("Turniket deleted!", true);
    }
    public ApiResponse getAll(){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();


        Set<Role> roles = user.getRoles();
        String role = RoleName.ROLE_STAFF.name();
        for (Role roleName : roles) {
            role = roleName.getName().name();
            break;
        }

        if (role.equals(RoleName.ROLE_DIRECTOR.name()))
            return new ApiResponse("Turniket List",true, turniketRepository.findAll());

        return new ApiResponse("Turniket List",true, turniketRepository.findAllByOwner(user));
    }
    public ApiResponse getByNumber(String number){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();


        Optional<Turniket> byNumber = turniketRepository.findByNumber(number);
        if (!byNumber.isPresent())
            return new ApiResponse("Turniket not found!", false);

        Set<Role> roles = byNumber.get().getOwner().getRoles();
        String role = null;
        for (Role roleName : roles) {
            role = roleName.getName().name();
            break;
        }
        boolean check = checker.check(role);

        if (byNumber.get().getOwner().getEmail().equals(user.getEmail()) || check){
            return new ApiResponse("Turniket", true, byNumber.get());
        }
        return new ApiResponse("You have no such right!", false);
    }

}
