package com.comrades.app.application.services.movie;

import com.comrades.app.application.responseObjects.ListResultDto;
import com.comrades.app.application.responseObjects.SingleResultDto;
import com.comrades.app.application.services.movie.dtos.MovieDto;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.UUID;

public interface IMovieQuery {
    ListResultDto<MovieDto> findAll() throws URISyntaxException, IOException, InterruptedException;

    SingleResultDto<MovieDto> findById(UUID id);
}
