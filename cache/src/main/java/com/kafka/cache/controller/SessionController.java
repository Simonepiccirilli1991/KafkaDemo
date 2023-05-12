package com.kafka.cache.controller;

import com.kafka.cache.error.SessionError;
import com.kafka.cache.model.request.SessionRequest;
import com.kafka.cache.service.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/session")
public class SessionController {

    @Autowired
    SessionService sessionService;

    @PostMapping("/create")
    public ResponseEntity<SessionService.SessionResponse> createSession(@RequestBody SessionRequest request){

        var resp = sessionService.createSession(request);
        HttpHeaders header = new HttpHeaders();
        header.add("sessionId",resp.sessionId());

        return ResponseEntity.ok().headers(header).body(new SessionService.SessionResponse(resp.created()));
    }

    @PostMapping("/update")
    public ResponseEntity<SessionService.SessionResponse> updateSession(@RequestHeader HttpHeaders header){

        if(ObjectUtils.isEmpty(header.getFirst("sessionId")))
            throw new SessionError("Invalid_Request","missing sessionID");

        var resp = sessionService.updateSession(header.getFirst("sessionId"));

        return ResponseEntity.ok().body(new SessionService.SessionResponse(resp.updated()));
    }
    @GetMapping("/getsession/{sessionID}")
    public ResponseEntity<SessionService.GetSession> getSession(@PathVariable ("sessionID") String sessionId){

        return ResponseEntity.ok(sessionService.getSession(sessionId));
    }

    @PostMapping("/check/session")
    public ResponseEntity<Boolean> checkSessionValid(@RequestHeader HttpHeaders headers){

        if(ObjectUtils.isEmpty(headers.getFirst("sessionId")))
            throw new SessionError("Invalid_Request","missing sessionID");

        return ResponseEntity.ok(sessionService.checkSessionValid(headers.getFirst("sessionId")));
    }
}
