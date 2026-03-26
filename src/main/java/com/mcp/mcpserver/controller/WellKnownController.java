package com.mcp.mcpserver.controller;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
public class WellKnownController {

  private static final String ACHILLES_BASE_URL = "https://flockmail-backend.flock-staging.com";

  @GetMapping("/.well-known/oauth-protected-resource")
  public Map<String, Object> oauthProtectedResource() {
    log.info("OAuth protected resource metadata requested");
    return Map.of(
      "resource", "http://localhost:8080",
      "authorization_servers", List.of(ACHILLES_BASE_URL)
    );
  }

//  @GetMapping("/.well-known/oauth-authorization-server")
//  public Map<String, Object> oauthAuthorizationServer() {
//    log.info("OAuth authorization server metadata requested");
//    return Map.of(
//      "issuer", ACHILLES_BASE_URL,
//      "authorization_endpoint", ACHILLES_BASE_URL + "/oauth2/authorize",
//      "token_endpoint", ACHILLES_BASE_URL + "/oauth2/token",
//      "response_types_supported", List.of("code"),
//      "grant_types_supported", List.of("authorization_code", "refresh_token"),
//      "code_challenge_methods_supported", List.of("S256"),
//      "token_endpoint_auth_methods_supported", List.of("none")
//    );

}