package com.comrades.app.api.controller;


import com.comrades.app.application.responseObjects.ListResultDto;
import com.comrades.app.application.responseObjects.SingleResultDto;
import com.comrades.app.application.services.movie.IMovieCommand;
import com.comrades.app.application.services.movie.IMovieQuery;
import com.comrades.app.application.services.movie.dtos.MovieDto;
import com.comrades.app.core.movie.usecases.ResultDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/movie")
@RequiredArgsConstructor
@SecurityScheme(
        name = "Basic Authentication",
        type = SecuritySchemeType.HTTP,
        scheme = "basic"
)
public class MovieController {

    private final IMovieCommand _movieCommand;
    private final IMovieQuery _movieQuery;

    @GetMapping("/get-all")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "List all Movies",
            tags = {"Movie"})
    public ListResultDto<MovieDto> getAll(@RequestParam("pageNumber") Optional<Integer> pageNumber, @RequestParam("pageSize") Optional<Integer> pageSize) {
        try {
            return _movieQuery.findAll();
        } catch (Exception ex) {
            return new ListResultDto<>(ex);
        }
    }

    @GetMapping(path = "{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(tags = {"Movie"})
    public SingleResultDto<MovieDto> findById(@PathVariable UUID id) {
        try {
            return _movieQuery.findById(id);
        } catch (Exception ex) {
            return new SingleResultDto<>(ex);
        }
    }

    @PostMapping("create")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(tags = {"Movie"})
    public SingleResultDto<MovieDto> save(@RequestBody MovieDto movie) {
        try {
            var result = _movieCommand.save(movie);
            return new SingleResultDto<>(result);
        } catch (Exception ex) {
            return new SingleResultDto<>(ex);
        }
    }

    @PostMapping("createBatch")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(tags = {"Movie"})
    public SingleResultDto<MovieDto> saveBatch(@RequestBody List<MovieDto> movies) {
        try {
            var result = _movieCommand.saveBatch(movies);
            return new SingleResultDto<>(result);
        } catch (Exception ex) {
            return new SingleResultDto<>(ex);
        }
    }

    @RequestMapping(
            path = "/uploadFile",
            method = RequestMethod.POST,
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(tags = {"Movie"})
    public ResultDto handleUpload(@RequestPart("file") MultipartFile file) throws IOException {
        try {
            var result = _movieCommand.processFile(file);
            return result;
        } catch (Exception ex) {
            return new ResultDto();
        }
    }

    @PutMapping("update")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(tags = {"Movie"})
    public SingleResultDto<MovieDto> update(@RequestBody MovieDto movie) {
        try {
            var result = _movieCommand.update(movie);
            return new SingleResultDto<>(result);
        } catch (Exception ex) {
            return new SingleResultDto<>(ex);
        }
    }

    @DeleteMapping(path = "/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(tags = {"Movie"})
    public SingleResultDto<MovieDto> delete(@PathVariable UUID id) {
        try {
            var result = _movieCommand.delete(id);
            return new SingleResultDto<>(result);
        } catch (Exception ex) {
            return new SingleResultDto<>(ex);
        }
    }

}

