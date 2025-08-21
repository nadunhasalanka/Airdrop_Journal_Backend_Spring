package com.airdrop_journal.backend.config.filters;


import com.airdrop_journal.backend.services.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component // Marks this as a Spring component, so it can be injected
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        System.out.println("====== JWT FILTER: STARTING FOR REQUEST TO " + request.getRequestURI() + " ======"); // DEBUG LINE 1

        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String userEmail;

        // 1. Check for the Authorization header and the "Bearer " prefix
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            System.out.println("====== JWT FILTER: NO BEARER TOKEN FOUND, PASSING TO NEXT FILTER ======"); // DEBUG LINE 2
            filterChain.doFilter(request, response); // If no token, pass to the next filter
            return;
        }

        // 2. Extract the token
        jwt = authHeader.substring(7);
        System.out.println("====== JWT FILTER: EXTRACTED TOKEN: " + jwt + " ======"); // DEBUG LINE 3

        // 3. Extract the user's email from the token
        try {
            userEmail = jwtService.extractUsername(jwt);
            System.out.println("====== JWT FILTER: EXTRACTED USER EMAIL: " + userEmail + " ======"); // DEBUG LINE 4
        } catch (Exception e) {
            System.out.println("====== JWT FILTER: FAILED TO EXTRACT USERNAME. ERROR: " + e.getMessage() + " ======"); // DEBUG LINE 5
            filterChain.doFilter(request, response); // Let it fail later with a 403
            return;
        }

        // 4. Check if we have an email and the user is not already authenticated
        if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            System.out.println("====== JWT FILTER: USER IS NOT AUTHENTICATED, LOADING FROM DATABASE ======"); // DEBUG LINE 6
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);

            // 6. Validate the token against the user's details
            if (jwtService.isTokenValid(jwt, userDetails)) {
                System.out.println("====== JWT FILTER: TOKEN IS VALID! AUTHENTICATING USER ======"); // DEBUG LINE 7
                // 7. If the token is valid, update the SecurityContext
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null, // We don't have credentials in a JWT-based flow
                        userDetails.getAuthorities()
                );
                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        // 8. Pass the request to the next filter in the chain
        filterChain.doFilter(request, response);
    }
}
