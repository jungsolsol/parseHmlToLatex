package nsd.open.config;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

/*
	스웨거 접속 URL
	http://localhost:8088/swagger-ui.html
 */
@SecurityScheme(
		name = "WHALE-NEOID",
		type = SecuritySchemeType.APIKEY,
		in = SecuritySchemeIn.HEADER
)
@Configuration
public class SwaggerConfig {

	@Bean
	public OpenAPI openAPI() {
		List<io.swagger.v3.oas.models.servers.Server> servers = new ArrayList<>();

		io.swagger.v3.oas.models.servers.Server server = new io.swagger.v3.oas.models.servers.Server();
		server.url("http://localhost:8088");
		server.description("로컬");
		servers.add(server);

		Info info = new Info();
		info.title("UBT-Whale API").version("v0.1");

		List<SecurityRequirement> securities = new ArrayList<>();
		SecurityRequirement security = new SecurityRequirement();
		security.addList("WHALE-NEOID");
		securities.add(security);

		return new OpenAPI()
				.info(info)
				.components(new Components())
				.security(securities)
				.servers(servers);
	}

	@Bean
	GroupedOpenApi commonRegistrationApis(){
		return GroupedOpenApi.builder().group("100. 교사>평가목록>평가만들기").pathsToMatch("/api-v1/tea/evalMng/**").build();
	}

}
