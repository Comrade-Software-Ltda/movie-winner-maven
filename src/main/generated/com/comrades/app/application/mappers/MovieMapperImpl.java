package com.comrades.app.application.mappers;

import com.comrades.app.application.services.movie.dtos.MovieDto;
import com.comrades.app.domain.models.Movie;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-08-29T11:02:55-0300",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 18.0.2.1 (Oracle Corporation)"
)
@Component
public class MovieMapperImpl implements MovieMapper {

    @Override
    public MovieDto toMovieDto(Movie movie) {
        if ( movie == null ) {
            return null;
        }

        MovieDto movieDto = new MovieDto();

        movieDto.setId( movie.getId() );
        if ( movie.getYear() != null ) {
            movieDto.setYear( String.valueOf( movie.getYear() ) );
        }
        movieDto.setTitle( movie.getTitle() );
        movieDto.setStudios( movie.getStudios() );
        movieDto.setWinner( movie.getWinner() );

        return movieDto;
    }

    @Override
    public Movie toMovie(MovieDto movieDto) {
        if ( movieDto == null ) {
            return null;
        }

        Movie movie = new Movie();

        movie.setId( movieDto.getId() );
        if ( movieDto.getYear() != null ) {
            movie.setYear( Integer.parseInt( movieDto.getYear() ) );
        }
        movie.setTitle( movieDto.getTitle() );
        movie.setStudios( movieDto.getStudios() );
        movie.setWinner( movieDto.getWinner() );

        return movie;
    }

    @Override
    public List<Movie> toListMovieDto(List<MovieDto> movies) {
        if ( movies == null ) {
            return null;
        }

        List<Movie> list = new ArrayList<Movie>( movies.size() );
        for ( MovieDto movieDto : movies ) {
            list.add( toMovie( movieDto ) );
        }

        return list;
    }
}
