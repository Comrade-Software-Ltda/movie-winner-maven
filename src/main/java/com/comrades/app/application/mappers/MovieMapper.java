package com.comrades.app.application.mappers;

import com.comrades.app.application.services.movie.dtos.MovieDto;
import com.comrades.app.domain.models.Movie;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MovieMapper {

    MovieMapper INSTANCE = Mappers.getMapper(MovieMapper.class);

    MovieDto toMovieDto(Movie movie);

    Movie toMovie(MovieDto movieDto);

    List<Movie> toListMovieDto(List<MovieDto> movies);
}
