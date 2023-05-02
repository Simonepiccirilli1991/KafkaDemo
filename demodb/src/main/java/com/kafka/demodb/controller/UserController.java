package com.kafka.demodb.controller;

import com.kafka.demodb.model.request.UserRequest;
import com.kafka.demodb.model.response.BaseDbResponse;
import com.kafka.demodb.service.CheckPinService;
import com.kafka.demodb.service.GetUserService;
import com.kafka.demodb.service.RegisterUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/user")
public class UserController {

    private final RegisterUserService registerService;
    private final CheckPinService checkPinService;
    private final GetUserService getUserService;

    public UserController(RegisterUserService registerService, CheckPinService checkPinService, GetUserService getUserService) {
        this.registerService = registerService;
        this.checkPinService = checkPinService;
        this.getUserService = getUserService;
    }

    @PostMapping("/register")
    public ResponseEntity<BaseDbResponse> registerUt(@RequestBody UserRequest request){
        return ResponseEntity.ok(registerService.registerUser(request));
    }

    @GetMapping("get/{userName}")
    public ResponseEntity<GetUserService.UserSummary> getUserUsername(@PathVariable ("userName") String username){
        return ResponseEntity.ok(getUserService.getUserFilterUsername(username));
    }
    @PostMapping("/checkpin")
    public ResponseEntity<BaseDbResponse> checkPinUser(@RequestBody UserRequest request){
        return ResponseEntity.ok(checkPinService.checkPinUser(request));
    }
}
