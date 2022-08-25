package com.comrades.app.application.services.movie.queries;

import com.comrades.app.application.mappers.MovieMapper;
import com.comrades.app.application.responseObjects.ListResultDto;
import com.comrades.app.application.responseObjects.SingleResultDto;
import com.comrades.app.application.services.movie.IMovieQuery;
import com.comrades.app.application.services.movie.dtos.MovieDto;
import com.comrades.app.persistence.repositories.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class MovieQuery implements IMovieQuery {

    private final MovieRepository _movieRepository;

    public ListResultDto<MovieDto> findAll() throws URISyntaxException, IOException, InterruptedException {
        var result = _movieRepository.findAll();
        var response = StreamSupport.stream(result.spliterator(),false)
                .map(x -> MovieMapper.INSTANCE.toMovieDto(x)).collect(Collectors.toList());

        return new ListResultDto<MovieDto>(response);
    }

    public SingleResultDto<MovieDto> findById(UUID id) {
        var result = _movieRepository.findById(id);
        var response = MovieMapper.INSTANCE.toMovieDto(result.get());
        return new SingleResultDto<MovieDto>(response);
    }
}
