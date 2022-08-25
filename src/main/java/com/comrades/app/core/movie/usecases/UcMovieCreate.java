package com.comrades.app.core.movie.usecases;

import com.comrades.app.core.bases.UseCase;
import com.comrades.app.domain.models.Movie;
import com.comrades.app.persistence.repositories.MovieRepository;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Instant;
import java.util.Date;


@Getter
@Setter
public class UcMovieCreate extends UseCase<Integer> {

    @Autowired
    private MovieRepository _movieRepository;

    private Movie movie;

    public UcMovieCreate(Movie movieInput) {
        super();
        movie = movieInput;
    }
    
    @Override
    protected Integer execute() throws Exception {
        return _movieRepository.save(movie);
    }
}
