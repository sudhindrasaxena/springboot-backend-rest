/**
 * Author: Sudhindra Kumar Saxena
 * User: sudhindra.saxena
 * Date: 02/06/25
 */

package com.sudhindra.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestConfig {
    @Bean
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }
}
