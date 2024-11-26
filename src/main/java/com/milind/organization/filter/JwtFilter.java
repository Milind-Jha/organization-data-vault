package com.milind.organization.filter;

import com.milind.organization.util.JwtTokenUtil;
import io.jsonwebtoken.Claims;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@AllArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserDetailsService customUserDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        final String authorizationHeader = request.getHeader("Authorization");

        String nameWithOrg = null;
        String jwt = null;

        // Check if the header contains a valid Bearer token
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwt = authorizationHeader.substring(7);
            try {
                Claims claims = jwtTokenUtil.extractAllClaims(jwt); // Extract full claims
                nameWithOrg = claims.get("name", String.class); // Extract username claim
                logger.info("Extracted Claims: " + claims); // Log all claims
            } catch (Exception e) {
                logger.error("Error while extracting claims from JWT: " + e.getMessage());
            }
        }

        // If username is valid and no authentication is set in the security context
        if (nameWithOrg != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            try {
                // Load user details by username with organization ID
                String[] userArray = nameWithOrg.split(":");
                System.out.println("\n"+userArray[0]+"\n");
                UserDetails userDetails = this.customUserDetailsService.loadUserByUsername(userArray[0]);
                // Validate the token using JwtTokenUtil
                if (jwtTokenUtil.validateToken(jwt, userDetails)) {
                    // Create authentication token
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    // Set authentication in the security context
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                } else {
                    logger.warn("Invalid JWT token for user: " + nameWithOrg);
                }
            } catch (UsernameNotFoundException e) {
                logger.error("User not found: " + nameWithOrg + " - " + e.getMessage());
            } catch (IllegalArgumentException e) {
                logger.error("Invalid arguments for JWT validation: " + e.getMessage());
            } catch (Exception e) {
                logger.error("Unexpected error during authentication for user: " + nameWithOrg, e);
            }

        }

        // Continue the filter chain
        chain.doFilter(request, response);
    }
}
