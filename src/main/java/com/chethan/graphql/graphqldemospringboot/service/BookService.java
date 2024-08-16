package com.chethan.graphql.graphqldemospringboot.service;

import com.chethan.graphql.graphqldemospringboot.model.Book;
import com.chethan.graphql.graphqldemospringboot.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;
import reactor.util.concurrent.Queues;

import java.util.List;

@Service
public class BookService {
  private final BookRepository bookRepository;
  private final Sinks.Many<Book> bookCreatedSink;

  @Autowired
  public BookService(BookRepository myBookRepository) {
    this.bookRepository = myBookRepository;
    // Create an instance for subscription
    this.bookCreatedSink = Sinks.many().multicast().onBackpressureBuffer(Queues.SMALL_BUFFER_SIZE, false);
  }

  public List<Book> getAllBooks() {
    return bookRepository.findAll();
  }

  public Book createBook(Book book) {
    Book createdBook = bookRepository.save(book);
    // Emit an event when a new Book is created
    bookCreatedSink.tryEmitNext(createdBook);

    return createdBook;
  }

  public Flux<Book> onBookCreated() {
    // Open a channel for our listener.
    return bookCreatedSink.asFlux();
  }
}
