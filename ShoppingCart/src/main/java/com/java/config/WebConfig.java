package com.java.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.client.RestTemplate;

@Configuration
@EnableScheduling
public class WebConfig {
    private final RestTemplate restTemplate = new RestTemplate();

    @Scheduled(fixedDelay = 24 * 60 * 1000)
    public void keepAwake() {
        String pingUrl = "https://thanhnhandev.herokuapp.com/ping";
        String result = restTemplate.getForObject(pingUrl, String.class);
        System.out.println(result);
    }
    @Scheduled(fixedDelay = 25 * 60 * 1000)
    public void keepAwakeEmail() {
        String pingUrl = "https://doan1thanhnhanvituong.herokuapp.com/ping";
        String result = restTemplate.getForObject(pingUrl, String.class);
        System.out.println(result);
    }
}