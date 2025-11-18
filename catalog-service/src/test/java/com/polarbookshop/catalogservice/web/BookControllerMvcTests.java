package com.polarbookshop.catalogservice.web;

import com.polarbookshop.catalogservice.domain.BookNotFoundException;
import com.polarbookshop.catalogservice.domain.BookService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BookController.class) //Identifies a test class that focuses on Spring MVC components, explicitly targeting BookController 
/* (Slice Tests) This only targets a specific part of the application, as it doesn't use
 * @SpringBootTest annotation, meaning this is a slice tests and only focuses on a specific 
 * part of the application.
 */ 
/*
 * @WebMvcTest annotation: which loads a Spring application context in a
 * mock web environment (no running server), configures the Spring MVC
 * infrastructure, and includes only the beans used by the MVC layer, like
 * @RestController and @RestControllerAdvice. 
 */
class BookControllerMvcTests {

    @Autowired
    private MockMvc mockMvc; //Utility class to test the web layer in a mock environment

    @MockBean //Adds a mock of BookService to the Spring application context
    private BookService bookService;

    @Test
    void whenGetBookNotExistingThenShouldReturn404() throws Exception {
        String isbn = "73737313940";
        given(bookService.viewBookDetails(isbn))
        .willThrow(BookNotFoundException.class); //Defines the expected behavior for the BookService mock bean
        mockMvc //MockMvc is used... 
        .perform(get("/books/" + isbn)) //to perform an HTTP GET request and verify the result.
        .andExpect(status().isNotFound()); //Expects the response to have a “404 Not Found” status
    }
}

/*
 * WARNING If you use IntelliJ IDEA, you might get a warning that MockMvc cannot be
 * autowired. Don’t worry. It’s a false positive. You can get rid of the warning by annotating the field
 * with @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection").
 */

/*
 * MockMvc is a utility class that lets you test web endpoints without loading a
 * server like Tomcat. Such a test is naturally lighter than the one we wrote in
 * CatalogServiceApplicationTests.java, where an embedded server was needed to run the test.
 * 
 * Slice tests run against an application context containing only the parts of the
 * configuration requested by that application slice. In the case of collaborating
 * beans outside the slice, such as the BookService class, we use mocks.
 * 
 * Mocks created with the @MockBean annotation are different from standard
 * mocks (for example, those created with Mockito) since the class is not only
 * mocked, but the mock is also included in the application context. Whenever
 * the context is asked to autowire that bean, it automatically injects the mock
 * rather than the actual implementation.
 */