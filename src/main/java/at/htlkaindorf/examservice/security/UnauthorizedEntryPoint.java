package at.htlkaindorf.examservice.security;

import at.htlkaindorf.examservice.pojos.AuthenticationErrorMessage;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Unauthorized Entry Point to be returned when there is an auth exception
 */
@Component
public class UnauthorizedEntryPoint implements AuthenticationEntryPoint {

    private ObjectMapper objectMapper;

    public UnauthorizedEntryPoint(
            ObjectMapper objectMapper
    ) {
        this.objectMapper = objectMapper;
    }

    @Override
    public void commence(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException authException
    ) throws IOException, ServletException {
        // Set the correct status code and header
        response.setStatus(HttpStatus.UNAUTHORIZED.value());

        response.setHeader(
                HttpHeaders.CONTENT_TYPE,
                "application/json"
        );

        // Create a new AuthenticationErrorMessage and set proper values
        AuthenticationErrorMessage msg = new AuthenticationErrorMessage();

        msg.setCode(HttpStatus.UNAUTHORIZED.value());
        msg.setMessage(HttpStatus.UNAUTHORIZED.getReasonPhrase());
        msg.setError(authException.getMessage());

        // Write the values to the output stream
        this.objectMapper
                .writerWithDefaultPrettyPrinter()
                .writeValue(response.getOutputStream(), msg);
    }
}
