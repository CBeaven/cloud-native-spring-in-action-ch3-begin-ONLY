package com.polarbookshop.catalogservice.domain;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;

public record Book(

    @NotBlank(message = "The book ISBN must be defined.")
    //The annotated element must match the specified regular expression (standard ISBN format).
    @Pattern(regexp = "^([0-9]{10}|[0-9]{13})$", message = "The ISBN format must be valid.")
    String isbn,

    //The annotated element must not be null and must contain at least one non-whitespace character.
    @NotBlank(message = "The book title must be defined.") 
    String title,

    //@NotBlank(message = "The book author must be defined.")
    String author,

    @NotNull(message = "The book price must be defined.")
    @Positive(message = "The book price must be greater than zero.")
    //The annotated element must not be null and must be greater than zero.
    Double price
) {}
