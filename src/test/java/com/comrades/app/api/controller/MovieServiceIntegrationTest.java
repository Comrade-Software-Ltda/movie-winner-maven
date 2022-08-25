package com.comrades.app.api.controller;

import com.comrades.app.application.services.movie.IMovieCommand;
import com.comrades.app.core.bases.UseCaseFacade;
import com.comrades.app.persistence.repositories.MovieRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import static org.mockito.MockitoAnnotations.openMocks;


@RunWith(SpringRunner.class)
@SpringBootTest
public class MovieServiceIntegrationTest {

    @Autowired
    private UseCaseFacade useCaseFacade;

    @Autowired
    private IMovieCommand _movieCommand;

    private MovieRepository movieRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Before
    public void setup() throws Exception {
        openMocks(this);

        DataSource dataSource = new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.H2)
                .addScript("classpath:jdbc/schema.sql")
                .addScript("classpath:jdbc/test-data.sql")
                .build();

        jdbcTemplate.setDataSource(dataSource);

        movieRepository = new MovieRepository(jdbcTemplate);
    }

    @Test
    public void testQueryMethod() throws IOException {
        MockMultipartFile file = new MockMultipartFile("file", "hello.csv",
                MediaType.TEXT_PLAIN_VALUE, new FileInputStream(new File("src/main/resources/movielist.csv")));


        var teste = _movieCommand.processFile(file);

        Assert.assertNotNull(teste);
    }

}
