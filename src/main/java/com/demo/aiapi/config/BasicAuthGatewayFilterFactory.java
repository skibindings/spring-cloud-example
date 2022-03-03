package com.demo.aiapi.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;

@Component
public class BasicAuthGatewayFilterFactory extends
        AbstractGatewayFilterFactory<BasicAuthGatewayFilterFactory.Config> {

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    public static class Config {
        private String username;
        private String password;
    }

    public BasicAuthGatewayFilterFactory() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            exchange.getRequest().mutate()
                    .headers(httpHeaders -> httpHeaders.setBasicAuth(
                            config.getUsername(),
                            config.getPassword()))
                    .build();
            return chain.filter(exchange);
        };
    }
}
