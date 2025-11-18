package com.polarbookshop.catalogservice.web;

import com.polarbookshop.catalogservice.domain.Book;
import com.polarbookshop.catalogservice.domain.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@RestController //Stereotype annotation marking a class as a Spring component and a source of handlers for REST endpoints
@RequestMapping("books") //Identifies the root path mapping URI for which the class provides handlers ("/books")
public class BookController{
    private final BookService bookService;

    public BookController(BookService bookService){
        this.bookService = bookService;
    }

    @GetMapping //Maps HTTP GET requests to the specific handler method
    public Iterable<Book> get(){
        return bookService.viewBookList();
    }

    @GetMapping("{isbn}") //A URI template variable appended to the root path mapping URI ("/books/{isbn}")
    public Book getByIsbn(@PathVariable String isbn){ //@PathVariable binds a method parameter to a URI template variable ({isbn})
        return bookService.viewBookDetails(isbn);
    }
    
    @PostMapping //Maps HTTP POST requests to the specific handler method
    @ResponseStatus(HttpStatus.CREATED) //Returns a 201 status if the book is created successfully
    public Book post(@Valid @RequestBody Book book){//@RequestBody binds a method parameter to the body of a web request
        return bookService.addBookToCatalog(book);
    }

    @DeleteMapping("{isbn}") //Maps HTTP DELETE requests to the specific handler method
    @ResponseStatus(HttpStatus.NO_CONTENT) //Returns a 204 status if the book is deleted successfully
    public void delete(@PathVariable String isbn){
        bookService.removeBookFromCatalog(isbn);
    }

    @PutMapping("{isbn}") //Maps HTTP PUT requests to the specific handler method
    public Book put(@PathVariable String isbn, @Valid @RequestBody Book book){
        return bookService.editBookDetails(isbn, book);
    }
}