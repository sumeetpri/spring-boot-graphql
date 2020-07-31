package com.book.graphql.springbootgraphql.repository;

import com.book.graphql.springbootgraphql.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book,String> {
}
