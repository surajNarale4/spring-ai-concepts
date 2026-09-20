package com.prod.config.tools;


import com.prod.dto.UserDto;
import com.prod.services.AuthService;
import com.prod.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserTool {

    private final AuthService authService;

    @Tool
    public ResponseEntity<UserDto> createUser(
            @ToolParam(description = "create user") UserDto userDto
    ){
        return ResponseEntity.ok(authService.signUp(userDto));
    }
}
