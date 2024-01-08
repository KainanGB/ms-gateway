package gateway.msgateway.configuration;


import jakarta.ws.rs.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

@Component
public class CustomGatewayFilter extends AbstractGatewayFilterFactory<CustomGatewayFilter.Config> {
    @Autowired
    private WebClient.Builder webClientBuilder;

    @Override
    public final GatewayFilter apply(Config config) {
        return ((exchange, chain) -> {
            final var request = exchange.getRequest();

            final var hasAuthorizationHeader = request.getHeaders().containsKey(HttpHeaders.AUTHORIZATION);

            if(!hasAuthorizationHeader) {
                throw new BadRequestException("missing authorization header");
            }

            String authHeader = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                authHeader = authHeader.substring(7);
            }

            return webClientBuilder.build()
                .get()
                .uri("http://localhost:8080/users/login")
                .header("Authorization", authHeader)
                .retrieve()
                .onStatus(HttpStatusCode::isError, response -> Mono.error(new BadRequestException("NOT AUTHORIZED")))
                .bodyToMono(Boolean.class).flatMap(res -> chain.filter(exchange));
        });
    }


    @Override
    public Class<Config> getConfigClass() {
        return Config.class;
    }

    @Override
    public Config newConfig() {
        return new Config();
    }

    public static class Config {
    }
}
