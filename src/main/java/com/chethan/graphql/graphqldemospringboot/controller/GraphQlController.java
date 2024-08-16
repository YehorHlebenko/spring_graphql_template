package com.chethan.graphql.graphqldemospringboot.controller;

import com.chethan.graphql.graphqldemospringboot.model.Book;
import com.chethan.graphql.graphqldemospringboot.service.BookService;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class GraphQlController {
    private final BookService bookService;
    @Autowired
    public GraphQlController(BookService myBookService){
        bookService = myBookService;
    }

    @SchemaMapping(typeName = "Subscription", field = "subscribeToBookChange")
    public Publisher<Book> subscribeToBookChange() {
        return bookService.onBookCreated();
    }

    @SchemaMapping(typeName = "Mutation", field = "registerBook")
    public String registerBook(@Argument Book bookData) {
        String response = null;
        try {
            Book createdBook = bookService.createBook(bookData);
            response = "Successfully saved book!";
        } catch (Exception e) {
            response = "Something went wrong! " + e;
        }
        return response;
    }

    @SchemaMapping(typeName = "Query", field = "findAllBooks")
    public List<Book> findAllBook(){
        return bookService.getAllBooks();
    }
}
