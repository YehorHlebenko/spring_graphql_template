package com.chethan.graphql.graphqldemospringboot.repository;

import com.chethan.graphql.graphqldemospringboot.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, String> {

}
