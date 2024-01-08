package gateway.msgateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableDiscoveryClient
public class MsGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsGatewayApplication.class, args);
	}

//	@Bean
//	public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
//		return builder.routes()
//			.route(r -> r.path("/users/**")
//				.uri("lb://ms-authentication"))
//
//			.route( r -> r.path("/management**")
//				.uri("lb://ms-management"))
//			.build();
//
//		// if you maintain the filters -> stripPrefix(1), you will need to but the MS-SERVICE prefix which in this case is ms-management
//
//		/*return builder.routes()
//			.route(r -> r.path("/auth/**")
//				.uri("lb://ms-authentication"))
//			.route( r -> r.path("/management/**")
//				.filters(f -> f.stripPrefix(1))
//				.uri("lb://ms-management"))
//			.build();*/
//	}
}
