package uz.hp.hrapp.controller;

import uz.hp.hrapp.payload.ApiResponse;
import uz.hp.hrapp.payload.TurniketHistoryDto;
import uz.hp.hrapp.service.TurniketHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/turniketHistory")
public class TurniketHistoryController {
    @Autowired
    TurniketHistoryService turniketHistoryService;

    @PostMapping
    public HttpEntity<?> enteringCompany(@RequestBody TurniketHistoryDto turniketHistoryDto){
        ApiResponse apiResponse = turniketHistoryService.enteringCompanyAdd(turniketHistoryDto);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }

}
