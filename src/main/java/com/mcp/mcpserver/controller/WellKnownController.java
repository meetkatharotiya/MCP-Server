package com.mcp.mcpserver.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class WellKnownController {

    private static final String ACHILLES_BASE_URL = "https://achilles.staging.flock.com";

    @GetMapping("/.well-known/oauth-authorization-server")
    public Map<String, Object> oauthAuthorizationServer() {
        return Map.of(
            "issuer", ACHILLES_BASE_URL,
            "authorization_endpoint", ACHILLES_BASE_URL + "/oauth2/authorize",
            "token_endpoint", ACHILLES_BASE_URL + "/oauth2/token",
            "scopes_supported", List.of("mcp.execute"),
            "response_types_supported", List.of("code"),
            "grant_types_supported", List.of("authorization_code", "refresh_token"),
            "code_challenge_methods_supported", List.of("S256")
        );
    }
}