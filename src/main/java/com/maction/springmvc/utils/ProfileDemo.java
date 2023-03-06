package com.maction.springmvc.utils;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("prod")
public class ProfileDemo {

    @Value("${test.message}")
    private String message;
    @Value("${test.prod-message}")
    private String prodMessage;

    @PostConstruct
    public void printProfileMessage() {
        System.out.println("Profile Message: " + message);
        System.out.println("Profile Message: " + prodMessage);
    }
}
