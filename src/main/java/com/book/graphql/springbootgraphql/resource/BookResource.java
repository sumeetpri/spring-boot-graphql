package com.book.graphql.springbootgraphql.resource;


import com.book.graphql.springbootgraphql.services.GraphQLService;
import graphql.ExecutionResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/rest/books")
@RestController
public class BookResource {

    @Autowired
    GraphQLService graphQLService;


    @PostMapping
    public ResponseEntity<Object> getAllBooks(@RequestBody String query){
        ExecutionResult execution = graphQLService.getGraphQL().execute(query);
        return new ResponseEntity<>(execution, HttpStatus.OK);
    }
}
