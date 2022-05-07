package uz.hp.hrapp.controller;

import uz.hp.hrapp.payload.ApiResponse;
import uz.hp.hrapp.payload.LoginDto;
import uz.hp.hrapp.security.JwtProvider;
import uz.hp.hrapp.service.MyAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class AuthController {

    @Autowired
    JwtProvider jwtProvider;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    MyAuthService myAuthService;


    @PostMapping("/login")
    public HttpEntity<?> logInToSystem(@RequestBody LoginDto loginDto) {

        try {
            //shunaqa tizim odami bormi tekshirish
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword()));

            String token = jwtProvider.generateToken(loginDto.getUsername());
            return ResponseEntity.ok(token);

        } catch (BadCredentialsException e) {
            return ResponseEntity.status(401).body("Login yoki Parolz notogri!");
        }

    }


    @GetMapping("/user/verifyEmail")
    public HttpEntity<?> verifyEmail(@RequestParam String email, @RequestParam String code){
        ApiResponse apiResponse  = myAuthService.verifyEmail(email,code);
        return ResponseEntity.status(apiResponse.isSuccess()?201:409).body(apiResponse);
    }


}
