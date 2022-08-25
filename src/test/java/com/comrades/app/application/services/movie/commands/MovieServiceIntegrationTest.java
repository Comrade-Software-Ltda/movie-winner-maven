package com.comrades.app.application.services.movie.commands;

import com.comrades.app.application.services.movie.IMovieCommand;
import com.comrades.app.application.services.movie.dtos.MovieDto;
import com.comrades.app.core.bases.UseCaseFacade;
import com.comrades.app.persistence.repositories.MovieRepository;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
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

    @BeforeAll
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
        Assert.assertEquals("Joel Silver", response.getMin().get(0).getProducer());
        Assert.assertEquals("Joel Silver", response.getMin().get(1).getProducer());

    }


    @Test
    public void testCheckDataFromFile() throws IOException {
        MockMultipartFile file = new MockMultipartFile("file", "hello.csv",
                MediaType.TEXT_PLAIN_VALUE, new FileInputStream(new File("src/main/resources/movielist.csv")));

        CsvMapper csvMapper = new CsvMapper();
        CsvSchema csvSchema = csvMapper
                .schemaFor(MovieDto.class)
                .withColumnSeparator(';')
                .withHeader();

        MappingIterator<MovieDto> orderLines = csvMapper.readerFor(MovieDto.class)
                .with(csvSchema)
                .readValues(file.getInputStream());

        var response = orderLines.readAll();

        Assert.assertNotNull(response);
        Assert.assertEquals(206, response.size());
    }

    @Test
    public void testCheckDataFromFileEmpty() throws IOException {
        MockMultipartFile file = new MockMultipartFile("file", "hello.csv",
                MediaType.TEXT_PLAIN_VALUE, new FileInputStream(new File("src/main/resources/movielist-empty.csv")));

        CsvMapper csvMapper = new CsvMapper();
        CsvSchema csvSchema = csvMapper
                .schemaFor(MovieDto.class)
                .withColumnSeparator(';')
                .withHeader();

        MappingIterator<MovieDto> orderLines = csvMapper.readerFor(MovieDto.class)
                .with(csvSchema)
                .readValues(file.getInputStream());

        var response = orderLines.readAll();

        Assert.assertNotNull(response);
        Assert.assertEquals(0, response.size());
    }

    @Test
    public void testCheckDataFromFileHeaderError() throws IOException {
        MockMultipartFile file = new MockMultipartFile("file", "hello.csv",
                MediaType.TEXT_PLAIN_VALUE, new FileInputStream(new File("src/main/resources/movielist-header-error.csv")));

        CsvMapper csvMapper = new CsvMapper();
        CsvSchema csvSchema = csvMapper
                .schemaFor(MovieDto.class)
                .withColumnSeparator(';')
                .withHeader();

        MappingIterator<MovieDto> orderLines = csvMapper.readerFor(MovieDto.class)
                .with(csvSchema)
                .readValues(file.getInputStream());

        var response = orderLines.readAll();

        Assert.assertNotNull(response);
        Assert.assertEquals(206, response.size());

    }

    @Test
    public void testCheckDataFromFileContentError() throws IOException {
        MockMultipartFile file = new MockMultipartFile("file", "hello.csv",
                MediaType.TEXT_PLAIN_VALUE, new FileInputStream(new File("src/main/resources/movielist-content-error.csv")));

        try {
            CsvMapper csvMapper = new CsvMapper();
            CsvSchema csvSchema = csvMapper
                    .schemaFor(MovieDto.class)
                    .withColumnSeparator(';')
                    .withHeader();

            MappingIterator<MovieDto> orderLines = csvMapper.readerFor(MovieDto.class)
                    .with(csvSchema)
                    .readValues(file.getInputStream());

            var response = orderLines.readAll();

            Assert.assertNotNull(response);
            Assert.assertEquals(206, response.size());
        } catch (Exception ex) {
            Assert.assertNotNull(ex);
        }

    }

}
