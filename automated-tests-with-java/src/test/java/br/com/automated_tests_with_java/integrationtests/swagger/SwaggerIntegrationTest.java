package br.com.automated_tests_with_java.integrationtests.swagger;

import br.com.automated_tests_with_java.config.TestConfigs;
import br.com.automated_tests_with_java.integrationtests.testcontainers.AbstractIntegrationTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class SwaggerIntegrationTest extends AbstractIntegrationTest {

	@DisplayName("JUnit test for should Display Swagger UI Page")
	@Test
	void testShouldDisplaySwaggerUiPage(){
		 var content = given().basePath("swagger-ui/index.html")
				.port(TestConfigs.SERVER_PORT)
				.when()
				.get()
				.then().statusCode(200)
				.extract().body()
				.asString();
		 assertTrue(content.contains("Swagger UI"));



	}

}
