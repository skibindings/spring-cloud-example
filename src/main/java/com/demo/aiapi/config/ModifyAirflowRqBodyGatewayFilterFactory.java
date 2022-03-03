package com.demo.aiapi.config;

import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.cloud.gateway.filter.factory.rewrite.ModifyRequestBodyGatewayFilterFactory;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Map;

@Component
public class ModifyAirflowRqBodyGatewayFilterFactory extends
        AbstractGatewayFilterFactory<ModifyAirflowRqBodyGatewayFilterFactory.Config> {

    @NoArgsConstructor
    public static class Config {
    }

    public ModifyAirflowRqBodyGatewayFilterFactory() {
        super(Config.class);
    }

    @Autowired
    private ModifyRequestBodyGatewayFilterFactory modifyRequestBodyGatewayFilterFactory;

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            ModifyRequestBodyGatewayFilterFactory.Config modifyRequestConfig = new ModifyRequestBodyGatewayFilterFactory.Config()
                    .setRewriteFunction(Map.class, Map.class,
                            (exchangeIn, originalRequestBody) -> Mono.just(modifyRequestBody(originalRequestBody)));
            return modifyRequestBodyGatewayFilterFactory.apply(modifyRequestConfig).filter(exchange, chain);
        };
    }

    private Map<String, Object> modifyRequestBody(Map<String, Object> originalRequestBody) {
        return Map.of("conf",originalRequestBody);
    }
}
