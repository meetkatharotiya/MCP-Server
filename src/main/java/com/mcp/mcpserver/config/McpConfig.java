package com.mcp.mcpserver.config;

import com.mcp.mcpserver.mcp.annotation.McpTool;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.tool.method.MethodToolCallbackProvider;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class McpConfig {

  @Bean
  public ToolCallbackProvider toolCallbackProvider(ApplicationContext context) {
    Object[] tools = context.getBeansWithAnnotation(McpTool.class).values().toArray();
    return MethodToolCallbackProvider.builder()
      .toolObjects(tools)
      .build();
  }
}