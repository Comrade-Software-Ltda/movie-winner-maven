package com.comrades.app.core.movie.usecases;

import com.comrades.app.core.bases.UseCase;
import com.comrades.app.domain.models.Movie;
import com.comrades.app.domain.models.Producer;
import com.comrades.app.persistence.repositories.MovieRepository;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


@Getter
@Setter
public class UcMovieCreateBatch extends UseCase<int[]> {

    @Autowired
    private MovieRepository _movieRepository;

    private List<Movie> movie;

    public UcMovieCreateBatch(List<Movie> movieInput) {
        super();
        movie = movieInput;
    }

    @Override
    protected int[] execute() throws Exception {
        _movieRepository.saveBatch(movie);

        var producers = movie.stream().map(Movie::getProducer).collect(Collectors.toList());

        List<Producer> allProducers = new ArrayList<>();
        for (var item : producers) {
            allProducers.addAll(item);
        }

        _movieRepository.saveProducers(allProducers);

        return null;
    }
}
