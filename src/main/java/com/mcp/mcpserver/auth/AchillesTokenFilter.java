package com.mcp.mcpserver.auth;

import com.flock.achillesclient.AchillesClient;
import com.flock.achillesclient.req.oauth2.OAuth2IntrospectRequest;
import com.flock.achillesclient.resp.oauth2.OAuth2IntrospectionResponse;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class AchillesTokenFilter extends OncePerRequestFilter {

    private static final String INTERNAL_CLIENT_ID = "internal_service";
    private static final String INTERNAL_SHARED_SECRET = "change-me-in-production";

    private final AchillesClient achillesClient;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            try {
                OAuth2IntrospectionResponse introspection = achillesClient.introspectToken(
                    new OAuth2IntrospectRequest()
                        .token(token)
                        .internalClientId(INTERNAL_CLIENT_ID)
                        .sharedSecret(INTERNAL_SHARED_SECRET)
                );
                if (introspection.active()) {
                    SecurityContextHolder.getContext().setAuthentication(
                        new UsernamePasswordAuthenticationToken(introspection.email(), null, List.of())
                    );
                    log.debug("Token valid for user: {}", introspection.email());
                } else {
                    log.warn("Token introspection returned active=false");
                }
            } catch (Exception e) {
                log.warn("Token validation failed: {}", e.getMessage(), e);
            }
        }

        chain.doFilter(request, response);
    }
}