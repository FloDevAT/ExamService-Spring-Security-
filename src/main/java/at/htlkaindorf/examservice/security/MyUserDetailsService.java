package at.htlkaindorf.examservice.security;

import at.htlkaindorf.examservice.entities.Student;
import at.htlkaindorf.examservice.repositories.StudentRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Custom implementation of the UserDetailsService which is required
 * for internal state management of Spring Security
 */
@Service
public class MyUserDetailsService implements UserDetailsService {

    private StudentRepository studentRepository;

    public MyUserDetailsService(
            StudentRepository studentRepository
    ) {
        this.studentRepository = studentRepository;
    }

    /**
     * Implementation of the required 'loadUserByUsername(String username)' method
     * @param username the username to look for
     * @return an UserDetails Object
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Check for user. If it does not exist -> throw exception
        Student student = this.studentRepository.findStudentByName(username)
                .orElseThrow(() -> new UsernameNotFoundException(
                        String.format("Username '%s' not found!", username)
                ));

        // create new UserDetails object
        UserDetails details = User.builder()
                .username(student.getName())
                .password(student.getPassword())
                .roles("USER") // in this app every user gets the same role
                .build();

        return details;
    }
}
