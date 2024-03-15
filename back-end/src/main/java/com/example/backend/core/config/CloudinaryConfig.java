package com.example.backend.core.config;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class CloudinaryConfig {

    @Value("dpljv1efr")
    private String cloudName;
    @Value("543118549338185")
    private String apiKey;
    @Value("3oOsn-ZHEkwio8SHMxF2e_vXLKc")
    private String apiSecret;

    @Bean
    public Cloudinary cloudinary(){
        Map<String,String> config = new HashMap<>();
        config.put("cloud_name",cloudName);
        config.put("api_key",apiKey);
        config.put("api_secret",apiSecret);
        return new Cloudinary(config);
    }
}
