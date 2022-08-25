package com.comrades.app.application.mappers;


import com.comrades.app.application.services.movie.dtos.MovieDto;
import com.comrades.app.domain.models.Movie;
import com.comrades.app.domain.models.Producer;
import org.mapstruct.AfterMapping;
import org.mapstruct.BeforeMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Mapper
public abstract class MovieEnhancedMapper {

    @BeforeMapping
    protected void enrichWithProducer(MovieDto movieDto, @MappingTarget Movie movie) {
        var splitComma = movieDto.getProducers().split(",");
        var splitAnd = movieDto.getProducers().split(" and ");

        List<String> allProducers = new ArrayList<>();
        Collections.addAll(allProducers,splitComma);
        Collections.addAll(allProducers,splitAnd);

        UUID uuidMovie = UUID.randomUUID();

        List<String> distinctElements = allProducers.stream()
                .distinct().toList();

        List<Producer> producers = new ArrayList<>();
        for (var item : distinctElements) {
            var prod = new Producer();
            prod.setId(UUID.randomUUID());
            prod.setMovieId(uuidMovie);
            prod.setName(item);
            producers.add(prod);
        }

        movieDto.setId(uuidMovie);
        movie.setProducer(producers);
    }

    public abstract List<Movie> toListMovieDto(List<MovieDto> movies);

}
