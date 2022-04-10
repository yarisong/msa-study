package com.example.apigatewayservice.config.filter;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.OrderedGatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class LoggingFilter extends AbstractGatewayFilterFactory<LoggingFilter.Config> {

    @Data
    public static class Config {
        // Put the configuration information
        private String baseMessage;
        private boolean preLogger;
        private boolean postLogger;
    }

    public LoggingFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {

        // Logging Filter
        GatewayFilter filter = new OrderedGatewayFilter((exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            ServerHttpResponse response = exchange.getResponse();

            log.info("Logging Filter baseMessage : {}", config.getBaseMessage());

            if (config.isPreLogger()) {
                log.info("Logging Pre Filter: request id -> {}", request.getId());
            }
            //Logging Post Filter
            return chain.filter(exchange).then(Mono.fromRunnable(() -> {

                if (config.isPostLogger()) {
                    log.info("Logging Post Filter : response code -> {}", response.getStatusCode());
                }
            }));
//        }, OrderedGatewayFilter.HIGHEST_PRECEDENCE);
        }, OrderedGatewayFilter.LOWEST_PRECEDENCE);

        return filter;
    }
}
