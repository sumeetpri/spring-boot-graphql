package com.book.graphql.springbootgraphql.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table
@Entity
public class Book {
    @Id
    private String isn;
    private String title;
    private String publisher;
    private String[] authers;
    private String publishedDate;
}
