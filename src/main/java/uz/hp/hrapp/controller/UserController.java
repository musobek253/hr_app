package uz.hp.hrapp.controller;

import uz.hp.hrapp.payload.ApiResponse;
import uz.hp.hrapp.payload.UserDto;
import uz.hp.hrapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.validation.Valid;


@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    UserService userService;

    //Yangi user qo'shish
    //MANAGER,DIREKTOR //PreAuthorize
    @PostMapping
    public HttpEntity<?> add(@Valid @RequestBody UserDto userDto) throws MessagingException {

        ApiResponse response = userService.add(userDto);
        return ResponseEntity.status(response.isSuccess() ? 200 : 409).body(response);
    }
    @GetMapping
    public HttpEntity<?> getByEmail(@RequestParam String email){
        ApiResponse apiResponse = userService.getByEmail(email);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }

}
