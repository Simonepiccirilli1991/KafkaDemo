package com.kafka.orc.controller;

import com.kafka.orc.model.request.RegisterRequest;
import com.kafka.orc.model.response.RegisterResponse;
import com.kafka.orc.service.RegistrationService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("api/orc/v1/user")
public class UserController {


    private final RegistrationService registrationService;

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> registerUser(@RequestBody RegisterRequest request){
        return ResponseEntity.ok(registrationService.registerUT(request));
    }

}
