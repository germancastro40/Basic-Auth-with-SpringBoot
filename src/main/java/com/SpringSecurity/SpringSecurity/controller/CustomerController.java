package com.SpringSecurity.SpringSecurity.controller;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("v1")
@AllArgsConstructor
@NoArgsConstructor
public class CustomerController {
    @Autowired
    private SessionRegistry sessionRegistry;

    @GetMapping("saludo")
    public ResponseEntity<String>string(){
        return ResponseEntity.ok("Hola mundo");
    }

    @GetMapping("saludo2")
    public ResponseEntity<String>saludo(){
        return ResponseEntity.ok("Hola mundo sin proteccion");
    }

    @GetMapping("public")
    public ResponseEntity<?>getDetailsSession() {
        String sessionID = "";
        User user = null;
        List<Object> sessions = sessionRegistry.getAllPrincipals();

        for (Object session: sessions){
            if (session instanceof User){
                user = (User) session;
            }
           List<SessionInformation> sessionInformations = sessionRegistry.getAllSessions(session, false);

            for(SessionInformation sessionInfo : sessionInformations){
                sessionID = sessionInfo.getSessionId();
            }
        }
        Map<String, Object> res = new HashMap<>();
        res.put("user", user);
        res.put("session_id", sessionID);

        return ResponseEntity.ok(res);
    }
}
