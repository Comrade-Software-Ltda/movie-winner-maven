package com.comrades.app.core.movie.usecases;

import com.comrades.app.core.bases.UseCase;
import com.comrades.app.persistence.repositories.MovieRepository;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;


@Getter
@Setter
public class UcMovieDelete extends UseCase<Integer> {

    @Autowired
    private MovieRepository _movieRepository;

    private UUID id;

    public UcMovieDelete(UUID movieId) {
        super();
        id = movieId;
    }

    @Override
    protected Integer execute() throws Exception {
        var result = _movieRepository.findById(id);

        return _movieRepository.delete(result.get().getId());
    }
}
