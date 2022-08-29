package com.comrades.app.application.mappers;

import com.comrades.app.application.services.movie.dtos.MovieDto;
import com.comrades.app.domain.models.Movie;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-08-29T11:02:55-0300",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 18.0.2.1 (Oracle Corporation)"
)
public class MovieEnhancedMapperImpl extends MovieEnhancedMapper {

    @Override
    public List<Movie> toListMovieDto(List<MovieDto> movies) {
        if ( movies == null ) {
            return null;
        }

        List<Movie> list = new ArrayList<Movie>( movies.size() );
        for ( MovieDto movieDto : movies ) {
            list.add( movieDtoToMovie( movieDto ) );
        }

        return list;
    }

    protected Movie movieDtoToMovie(MovieDto movieDto) {
        if ( movieDto == null ) {
            return null;
        }

        Movie movie = new Movie();

        enrichWithProducer( movieDto, movie );

        movie.setId( movieDto.getId() );
        if ( movieDto.getYear() != null ) {
            movie.setYear( Integer.parseInt( movieDto.getYear() ) );
        }
        movie.setTitle( movieDto.getTitle() );
        movie.setStudios( movieDto.getStudios() );
        movie.setWinner( movieDto.getWinner() );

        return movie;
    }
}
