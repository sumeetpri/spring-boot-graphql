package com.book.graphql.springbootgraphql.services;

import com.book.graphql.springbootgraphql.model.Book;
import com.book.graphql.springbootgraphql.repository.BookRepository;
import com.book.graphql.springbootgraphql.services.datafetcher.AllBooksDataFetcher;
import com.book.graphql.springbootgraphql.services.datafetcher.BookDataFetcher;
import graphql.GraphQL;
import graphql.schema.DataFetcher;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.util.stream.Stream;


@Service
public class GraphQLService {

    @Autowired
    BookRepository bookRepository;


    @Value("classpath:books.graphql")
    Resource resource;

    private GraphQL  graphQL;
    @Autowired
    private AllBooksDataFetcher allBookDataFetcher;
    @Autowired
    private BookDataFetcher bookDataFetchers;

    @PostConstruct
    public void loadSchema() throws IOException {
        File schemaFile = resource.getFile();


        loadDataIntoHSQL();

        TypeDefinitionRegistry typeDefinitionRegistry = new SchemaParser().parse(schemaFile);
        RuntimeWiring writing = buildRuntimewriting();
        GraphQLSchema schema =  new SchemaGenerator().makeExecutableSchema(typeDefinitionRegistry,writing);
        graphQL = GraphQL.newGraphQL(schema).build();

    }

    private void loadDataIntoHSQL() {

        Stream.of(
                new Book("123","Book of lord","Kindle Editoin", new String[]{"Sumeet Yadav","Satwik"},"Nov 2017"),
                new Book("124","Book of god","Kindle Editoin", new String[]{" Yadav","dhawal"},"Nov 2015"),
                new Book("125","Book of computer","Kindle Editoin", new String[]{" Yadav","Satwik"},"Nov 2014"),
                new Book("126","Book of dragen","Kindle Editoin", new String[]{" Yadav","Avijeet"},"Nov 2016")
        ).forEach(book -> bookRepository.save(book));
    }


    private RuntimeWiring buildRuntimewriting() {
        return RuntimeWiring.newRuntimeWiring()
                .type("Query",typewring ->{
                    return typewring
                            .dataFetcher("allBooks",allBookDataFetcher)
                            .dataFetcher("book",bookDataFetchers);
                        })

                .build();
    }

    public GraphQL getGraphQL(){
        return graphQL;
    }
}
