package com.comrades.app.persistence.repositories;

import com.comrades.app.domain.models.Movie;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IMovieRepository {
    List<Movie> findAll();
    List<Movie> findMovieFromProducerWithMoreThanOrEqualTwoWinners();
    int save(Movie movie);
    int edit(Movie movie);
    int delete(UUID id);
    int deleteAllMovies();
    int deleteAllProducers();
    Optional<Movie> findById(UUID id);
}
