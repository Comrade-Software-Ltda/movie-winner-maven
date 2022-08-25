package com.comrades.app.core.movie.usecases;

import com.comrades.app.core.bases.UseCase;
import com.comrades.app.domain.models.Movie;
import com.comrades.app.persistence.repositories.MovieRepository;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;


@Getter
@Setter
public class UcMovieEdit extends UseCase<Integer> {

    @Autowired
    private MovieRepository _movieRepository;

    private Movie movie;

    public UcMovieEdit(Movie movieInput) {
        super();
        movie = movieInput;
    }

    @Override
    protected Integer execute() throws Exception {
        var result = _movieRepository.findById(movie.getId());
        if(result != null) {
            return _movieRepository.edit(movie);
        }
        return 0;
    }
}
