package com.comrades.app.application.services.movie.commands;

import com.comrades.app.application.services.movie.IMovieCommand;
import com.comrades.app.core.bases.UseCaseFacade;
import com.comrades.app.persistence.repositories.MovieRepository;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
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
import java.util.Optional;

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
                .build();

        jdbcTemplate.setDataSource(dataSource);

        movieRepository = new MovieRepository(jdbcTemplate);
    }

    @Test
    public void testProcessFile() throws IOException {
        MockMultipartFile file = new MockMultipartFile("file", "hello.csv",
                MediaType.TEXT_PLAIN_VALUE, new FileInputStream(new File("src/main/resources/movielist.csv")));


        var response = _movieCommand.processFile(file);

        Assert.assertNotNull(response);
        Assert.assertNotNull(response.getMin());
        Assert.assertNotNull(response.getMin().get(0));
        Assert.assertNotNull(response.getMin().get(1));
        Assert.assertNotNull(response.getMax());
        Assert.assertEquals(response.getMin().get(0).getProducer(), "Joel Silver");
        Assert.assertEquals(response.getMin().get(1).getProducer(), "Joel Silver");

    }

}
