package com.dextra.hp.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class FeignInterceptor implements RequestInterceptor {

    @Value("${AUTH_KEY}")
    public String authKey;

    @Override
    public void apply(RequestTemplate requestTemplate) {
        requestTemplate.query("key", authKey);
    }
}
