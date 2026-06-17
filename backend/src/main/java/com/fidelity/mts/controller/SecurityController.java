package com.fidelity.mts.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.security.Principal;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class SecurityController {
    @GetMapping("/auth")
    public ResponseEntity<String> authenticate() {
        return ResponseEntity.ok("Authenticated");
    }

}
