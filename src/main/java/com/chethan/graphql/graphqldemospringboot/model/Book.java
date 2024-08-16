package com.chethan.graphql.graphqldemospringboot.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "book")
public class Book {
  @Id
  private String title;
  private String id;
}
