package com.comrades.app.application.services.movie;

import com.comrades.app.application.services.movie.dtos.MovieDto;
import com.comrades.app.core.movie.usecases.ResultDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

public interface IMovieCommand {
    Integer save(MovieDto movie);

    int[] saveBatch(List<MovieDto> movies);

    ResultDto processFile(MultipartFile file) throws IOException;

    Integer update(MovieDto movie);

    Integer delete(UUID id);
}
