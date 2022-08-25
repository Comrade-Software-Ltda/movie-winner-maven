package com.comrades.app.core.movie.usecases;

import com.comrades.app.domain.models.Movie;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResultDto {

    ResultDto(ProcessIntervalBetweenWinnerDto _min, ProcessIntervalBetweenWinnerDto _max) {

        var producerFastestFirstMovie = new ResultItemDto(_min.getMovies().get(0).getProducerName(), _min.getYearDifference(), _min.getPreviousWin(), _min.getMovies().get(1).getYear());
        var producerFastestLastMovie = new ResultItemDto(_min.getMovies().get(1).getProducerName(), _min.getYearDifference(), _min.getMovies().get(0).getYear(), _min.getFollowingWin());

        var producerLongestFirstMovie = new ResultItemDto(_max.getMovies().get(0).getProducerName(), _max.getYearDifference(), _max.getPreviousWin(), _max.getMovies().get(1).getYear());
        var producerLongestLastMovie = new ResultItemDto(_max.getMovies().get(1).getProducerName(), _max.getYearDifference(), _max.getMovies().get(0).getYear(), _max.getFollowingWin());

        min = new ArrayList<>() {{
            add(producerFastestFirstMovie);
            add(producerFastestLastMovie);
        }};

        max = new ArrayList<>() {{
            add(producerLongestFirstMovie);
            add(producerLongestLastMovie);
        }};
    }

    private List<ResultItemDto> min;

    private List<ResultItemDto> max;
}
