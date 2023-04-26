package com.kafka.demodb.controller;

import com.kafka.demodb.model.request.UserRequest;
import com.kafka.demodb.model.response.BaseDbResponse;
import com.kafka.demodb.service.RegisterUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/user")
public class UserController {

    @Autowired
    RegisterUserService registerService;


    @PostMapping("/register")
    public ResponseEntity<BaseDbResponse> registerUt(@RequestBody UserRequest request){
        return ResponseEntity.ok(registerService.registerUser(request));
    }
}
