package com.example.account.management.accountmanager.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

@Configuration
@ComponentScan(basePackages = "com.example.account.management.accountmanager")
@EnableAsync
public class AppConfig {

}
