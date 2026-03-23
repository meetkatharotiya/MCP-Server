package com.mcp.mcpserver.config;

import com.flock.achillesclient.AchillesClient;
import com.flock.achillesclient.AchillesClientConfig;
import com.flock.suite.model.AppService;
import com.flock.suite.model.Environment;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.flock.suite.model.Environment.STAGING;

@Configuration
public class AchillesConfig {

    @Bean
    public AchillesClient achillesClient() {
        return AchillesClientConfig.newConfig(STAGING, AppService.hephaestus)
                .getClient();
    }
}