package com.polarbookshop.catalogservice;

import com.polarbookshop.catalogservice.domain.Book;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;
import static org.assertj.core.api.Assertions.assertThat;

// Loads a full Spring web application context and a Servlet container listening on a random port
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

class CatalogServiceApplicationTests {

	@Test
	void contextLoads() {
	}

	@Autowired
	private WebTestClient webTestClient; //Utility to perform REST calls for testing
	@Test
	void whenPostRequestThenBookCreated() {
		var expectedBook = new Book("1231231231", "Title", "Author", 9.90);
		webTestClient
		.post() //Sends an HTTP POST request
		.uri("/books") //Sends the request to the "/books" endpoint
		.bodyValue(expectedBook) //Adds the book in the request body
		.exchange() //Sends the request
		.expectStatus().isCreated() //Verifies that the HTTP response has status “201 Created”
		.expectBody(Book.class).value(actualBook -> {
			assertThat(actualBook).isNotNull(); //Verifies that the HTTP response has a non-null body
			assertThat(actualBook.isbn())
			.isEqualTo(expectedBook.isbn()); //Verifies that the created object is as expected
		});
	}
}

/*
 * NOTE You might be wondering why I didn’t use constructor-based dependency injection in listing
 * 3.17, considering that I previously stated that’s the recommended option. Using field-based
 * dependency injection in production code has been deprecated and is strongly discouraged, but it’s still
 * acceptable to autowire dependencies in a test class. In every other scenario, I recommend sticking with
 * constructor-based dependency injection for the reasons I explained earlier. For more information, you
 * can refer to the official Spring Framework documentation (https://spring.io/projects/springframework).
 */
