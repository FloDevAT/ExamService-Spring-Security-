package at.htlkaindorf.examservice.controllers;

import at.htlkaindorf.examservice.pojos.Credentials;
import at.htlkaindorf.examservice.services.AuthService;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * A controller with routes that can be accessed by anybody (no auth required -> SecurityConfiguration.java:40)
 *
 */
@Controller()
@RequestMapping("public")
public class PublicController {

    private AuthService authService;

    public PublicController(
            AuthService authService
    ) {
        this.authService = authService;
    }

    /**
     * Endpoint to authenticate a user (username + password)
     * @param credentials username + password combination
     * @return the newly created JWT
     */
    @PostMapping("signin")
    public ResponseEntity<String> signin(
            @RequestBody Credentials credentials
    ) {
        String token = this.authService.signIn(credentials);
        return ResponseEntity.ok(token);
    }

}
