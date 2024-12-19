package tr.edu.ogu.lab2.controller;

import static io.restassured.RestAssured.given;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import tr.edu.ogu.lab2.common.AbstractIntegrationTest;
import tr.edu.ogu.lab2.model.CountryDTO;
import tr.edu.ogu.lab2.service.CountryService;


public class CountryControllerTests extends AbstractIntegrationTest {


	@MockBean
	private CountryService service;

	private String countryListEndpoint = "/v1/countries";
	
	@BeforeEach
	void setUp() {
		RestAssured.baseURI = "http://localhost:" + port;
		RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
	}

	@Test
	void shouldReturnStatus200_WhenCountrySyncRequest() throws Exception {
		

		CountryDTO countryDto = CountryDTO.builder()
				.id(1L)
				.name("Turkey")
				.code("TR")
				.build();

		when(service.syncCountries()).thenReturn(Arrays.asList(countryDto));

		Response response = given()
				.contentType(ContentType.JSON)
				.when().post(countryListEndpoint);

		response.then().statusCode(200);
	}
}
