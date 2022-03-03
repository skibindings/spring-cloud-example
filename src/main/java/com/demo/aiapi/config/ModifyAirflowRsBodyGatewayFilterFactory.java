package com.demo.aiapi.config;

import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.OrderedGatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.cloud.gateway.filter.factory.rewrite.ModifyRequestBodyGatewayFilterFactory;
import org.springframework.cloud.gateway.filter.factory.rewrite.ModifyResponseBodyGatewayFilterFactory;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Map;

@Component
public class ModifyAirflowRsBodyGatewayFilterFactory extends
        AbstractGatewayFilterFactory<ModifyAirflowRsBodyGatewayFilterFactory.Config> {

    @NoArgsConstructor
    public static class Config {
    }

    public ModifyAirflowRsBodyGatewayFilterFactory() {
        super(Config.class);
    }

    @Autowired
    private ModifyResponseBodyGatewayFilterFactory modifyResponseBodyGatewayFilterFactory;

    @Override
    public GatewayFilter apply(Config config) {
        return new OrderedGatewayFilter((exchange, chain) -> {
            ModifyResponseBodyGatewayFilterFactory.Config modifyResponseConfig = new ModifyResponseBodyGatewayFilterFactory.Config()
                    .setRewriteFunction(Map.class, Map.class,
                            (exchangeOut, originalResponseBody) -> Mono.just(modifyResponseBody(originalResponseBody)));
            return modifyResponseBodyGatewayFilterFactory.apply(modifyResponseConfig).filter(exchange, chain);
        }
        ,-2);
    }

    private Map<String, Object> modifyResponseBody(Map<String, Object> originalResponseBody) {
        return Map.of("run_id",originalResponseBody.get("dag_run_id"));
    }
}
