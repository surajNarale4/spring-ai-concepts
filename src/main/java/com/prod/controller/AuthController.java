package com.prod.controller;

import com.prod.dto.LoginRequestDto;
import com.prod.dto.LoginResponseDTO;
import com.prod.dto.UserDto;
import com.prod.services.AuthService;
import com.prod.services.JwtService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.micrometer.observation.autoconfigure.ObservationProperties;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final JwtService jwtService;

    @PostMapping("/signup")
    public ResponseEntity<UserDto> signup(@RequestBody UserDto userDto){

        UserDto user= authService.signUp(userDto);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDto login, HttpServletRequest request , HttpServletResponse response){
        LoginResponseDTO d = authService.login(login);
        Cookie accessToken = new Cookie("accessToken",d.getAccessToken());
        Cookie refreshToken= new Cookie("refreshToken",d.getRefreshToken());

        accessToken.setHttpOnly(true);
        accessToken.setSecure(true);
        refreshToken.setHttpOnly(true);
        refreshToken.setSecure(true);

        response.addCookie(accessToken);
        response.addCookie(refreshToken);

        return ResponseEntity.ok(d);
    }

    @PostMapping("/refresh")
    public String refreshToken(HttpServletRequest request,HttpServletResponse response){
        Cookie[] cookies=request.getCookies();
        return authService.generateRefreshToken(cookies);

    }



    @GetMapping("/hello")
    public String sayHello(){
        return "Hello";
    }


}
