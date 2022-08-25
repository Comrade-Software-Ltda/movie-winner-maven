package com.comrades.app.application.services.movie.commands;

import com.comrades.app.application.mappers.MovieEnhancedMapper;
import com.comrades.app.application.mappers.MovieMapper;
import com.comrades.app.application.services.movie.IMovieCommand;
import com.comrades.app.application.services.movie.dtos.MovieDto;
import com.comrades.app.core.movie.usecases.*;
import com.comrades.app.core.bases.UseCaseFacade;
import com.comrades.app.persistence.repositories.MovieRepository;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class MovieCommand implements IMovieCommand {

    private final UseCaseFacade facade;

    @Autowired
    private MovieRepository _movieRepository;

    public ResultDto processFile(MultipartFile file) throws IOException {

        List<MovieDto> allMovies = getDataFromFile(file);
        saveBatch(allMovies);

        var uc = new UcMovieGetSmallestDateDifferenceBetweenWinner();
        var response = facade.execute(uc);

        return response;

    }

    public Integer save(MovieDto movie) {
        var result = MovieMapper.INSTANCE.toMovie(movie);
        var uc = new UcMovieCreate(result);
        return facade.execute(uc);
    }

    private MovieEnhancedMapper sut = Mappers.getMapper(MovieEnhancedMapper.class);

    public int[] saveBatch(List<MovieDto> movies) {
        var result = sut.toListMovieDto(movies);
        var uc = new UcMovieCreateBatch(result);
        return facade.execute(uc);
    }

    private List<MovieDto> getDataFromFile(MultipartFile file) throws IOException {
        CsvMapper csvMapper = new CsvMapper();
        CsvSchema csvSchema = csvMapper
                .schemaFor(MovieDto.class)
                .withColumnSeparator(';')
                .withHeader();

        MappingIterator<MovieDto> orderLines = csvMapper.readerFor(MovieDto.class)
                .with(csvSchema)
                .readValues(file.getInputStream());

        return orderLines.readAll();
    }

    public Integer update(MovieDto movie) {
        var result = MovieMapper.INSTANCE.toMovie(movie);
        var uc = new UcMovieEdit(result);
        return facade.execute(uc);
    }

    public Integer delete(UUID id) {
        var uc = new UcMovieDelete(id);
        return facade.execute(uc);
    }
}
