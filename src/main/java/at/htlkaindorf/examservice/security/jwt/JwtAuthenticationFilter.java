package at.htlkaindorf.examservice.security.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private JwtUtilities utilities;
    private UserDetailsService userDetailsService;

    public JwtAuthenticationFilter(
            JwtUtilities utilities,
            UserDetailsService userDetailsService
    ) {
        this.utilities = utilities;
        this.userDetailsService = userDetailsService;
    }

    /**
     * This method checks for following things:
     *  1.) is there an Authorization-Header + does it start with 'Bearer '?
     *  2.) is the token in there valid?
     *  3.) Can a username be extracted from it?
     *  4.) Is there a valid user?
     *  5.) is someone already authenticated?
     *  6.) creates new authentication, sets the context and proceeds
     */
    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        // 1.) is there an Authorization-Header + does it start with 'Bearer '?
        String headerValue = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (headerValue == null || !headerValue.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // 2.) is the token in there valid?
        String token = headerValue.substring(
                7 // After "Bearer "
        );

        if (!this.utilities.isTokenValid(token)) {
            filterChain.doFilter(request, response);
            return;
        }

        // 3.) Can a username be extracted from it?
        String username = this.utilities.extractUsername(token);

        if (username == null || username.isEmpty()) {
            filterChain.doFilter(request, response);
            return;
        }

        // 4.) Is there a valid user?
        UserDetails details = this.userDetailsService.loadUserByUsername(username);

        // 5.) is someone already authenticated?
        if (SecurityContextHolder.getContext().getAuthentication() != null) {
            filterChain.doFilter(request, response);
            return;
        }

        // 6.) creates new authentication, sets the context and proceeds
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(
                new UsernamePasswordAuthenticationToken(
                        details,
                        null,
                        details.getAuthorities()
                )
        );
        SecurityContextHolder.setContext(context);

        filterChain.doFilter(request, response);
    }
}
