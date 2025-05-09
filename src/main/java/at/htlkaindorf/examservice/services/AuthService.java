package at.htlkaindorf.examservice.services;

import at.htlkaindorf.examservice.entities.Student;
import at.htlkaindorf.examservice.pojos.Credentials;
import at.htlkaindorf.examservice.repositories.StudentRepository;
import at.htlkaindorf.examservice.security.jwt.JwtUtilities;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private AuthenticationManager authenticationManager;
    private StudentRepository studentRepository;
    private JwtUtilities jwtUtilities;

    public AuthService(
            AuthenticationManager manager,
            StudentRepository studentRepository,
            JwtUtilities jwtUtilities
    ) {
        this.authenticationManager = manager;
        this.studentRepository = studentRepository;
        this.jwtUtilities = jwtUtilities;
    }

    public String signIn(
            Credentials credentials
    ) {
        Student student = this.studentRepository.findStudentByName(
                credentials.getUsername()
        ).orElseThrow(() -> new UsernameNotFoundException(
                String.format("User '%s' not found!", credentials.getUsername())
        ));

        Authentication authentication = this.authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        credentials.getUsername(),
                        credentials.getPassword()
                )
        );

        return this.jwtUtilities.generateToken(credentials.getUsername());
    }

}
