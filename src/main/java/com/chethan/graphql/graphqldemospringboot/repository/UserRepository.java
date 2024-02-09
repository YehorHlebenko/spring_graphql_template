package com.chethan.graphql.graphqldemospringboot.repository;

import com.chethan.graphql.graphqldemospringboot.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

}
