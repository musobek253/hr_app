package uz.hp.hrapp.service;

import uz.hp.hrapp.component.Checker;
import uz.hp.hrapp.entity.Role;
import uz.hp.hrapp.entity.Turniket;
import uz.hp.hrapp.entity.TurniketHistory;
import uz.hp.hrapp.entity.enums.RoleName;
import uz.hp.hrapp.payload.ApiResponse;
import uz.hp.hrapp.payload.TurniketHistoryDto;
import uz.hp.hrapp.repository.TurniketHistoryRepository;
import uz.hp.hrapp.repository.TurniketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class TurniketHistoryService {


    @Autowired
    TurniketHistoryRepository turniketHistoryRepository;
    @Autowired
    TurniketRepository turniketRepository;
    @Autowired
    Checker checker;

    public ApiResponse enteringCompanyAdd(TurniketHistoryDto turniketHistoryDto){
        Optional<Turniket> byId = turniketRepository.findById(turniketHistoryDto.getTurniketNumber());
        if (!byId.isPresent()) return new ApiResponse("Turniket not found,no access to enter",false);
        Turniket turniket = byId.get();
        Set<Role> roles = turniket.getOwner().getRoles();
        String role = RoleName.ROLE_STAFF.name();
        for (Role role1 : roles) {
            role = role1.getName().name();
            break;
        }

        boolean check = checker.check(role);
        if (!check) return new ApiResponse("Do not access to enter",false);
        TurniketHistory turniketHistory=new TurniketHistory();
        turniketHistory.setTurniket(turniket);
        turniketHistory.setType(turniketHistoryDto.getType());
        TurniketHistory save = turniketHistoryRepository.save(turniketHistory);
        return new ApiResponse("Welcome",true,save.getTurniket().getOwner());

    }
    public ApiResponse getAllByDate(String number, Timestamp startTime, Timestamp endTime){
        Optional<Turniket> optionalTurniket = turniketRepository.findByNumber(number);
        if (!optionalTurniket.isPresent())
            return new ApiResponse("Karochchi brat ruxsati yo'q joyga kirmoqchi bo'lyapti! Bunaqa turniket yoq.", false);

        Set<Role> roles = optionalTurniket.get().getOwner().getRoles();
        String role = RoleName.ROLE_STAFF.name();
        for (Role role1 : roles) {
            role = role1.getName().name();
            break;
        }

        boolean check = checker.check(role);
        if (!check)
            return new ApiResponse("Karochchi brat ruxsatiz yo'q joyga kirmoqchi bo'lyapsiz! Sizda huquq yo'q!", false);

        List<TurniketHistory> historyList = turniketHistoryRepository.findAllByTurniketAndTimeIsBetween(optionalTurniket.get(), startTime, endTime);
        return new ApiResponse("History list by date",true, historyList);
    }
}
