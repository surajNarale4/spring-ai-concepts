package com.prod.controller;


import com.prod.dto.AiRequest;
import com.prod.services.AiService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequestMapping(path= "api")
@RequiredArgsConstructor
@RestController
public class AiController {

    private final AiService aiService;

    @PostMapping("/ai")
    public String message(@RequestBody AiRequest aiRequest , @RequestParam("message") String message){
        return aiService.message(aiRequest,message);
    }

    @GetMapping("/bi")
    public String message1(){
        return "Hello";
    }
}
