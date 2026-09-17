package com.prod.services;

import com.prod.dto.LoginRequestDto;
import com.prod.dto.LoginResponseDTO;
import com.prod.dto.UserDto;
import com.prod.entities.User;
import com.prod.repositories.UserRepository;
import jakarta.servlet.http.Cookie;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private static final Logger log= LoggerFactory.getLogger(AuthService.class);

    public UserDto signUp(UserDto userDto){
        Optional<User> user= userRepository.findByEmail(userDto.getEmail());
        if(user.isPresent()){
            throw new BadCredentialsException("User with email already exixts"+ userDto.getEmail());
        }

        User toCreate= modelMapper.map(userDto, User.class);
        log.debug("Going create user in db as {} from {} ",toCreate,userDto);
        toCreate.setPassword(passwordEncoder.encode(toCreate.getPassword()));
        User saveUser= userRepository.save(toCreate);
        return modelMapper.map(saveUser, UserDto.class);



    }

    public LoginResponseDTO login(LoginRequestDto login) {
        log.debug("login password : "+login.getPassword());
        log.debug("for login user encoded password is : "+passwordEncoder.encode(login.getPassword()));
        Authentication authentication=authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(login.getEmail(), login.getPassword())
        );

        User user= (User)authentication.getPrincipal();
        String accessToken= jwtService.generateJWT(user);
        String refreshToken=jwtService.generateRefreshToken(jwtService.getIdFromToken(accessToken));
        return new LoginResponseDTO(accessToken,refreshToken);

    }


    public String generateRefreshToken(Cookie[] cookies) {

        if(cookies == null){
            throw new RuntimeException("no refresh token found");
        }
        for(Cookie cookie : cookies){
            if(cookie.getName().equals("refreshToken")){
                String refreshToken=cookie.getValue();
                Long id=jwtService.getIdFromToken(refreshToken); //chacking valid or not
                User user=userRepository.findById(id)
                        .orElseThrow((
                        )->new RuntimeException("no user found with given id"+ id)
                        );
                return jwtService.generateJWT(user);
            }
        }
        throw new RuntimeException("no refresh token found");
    }
}
