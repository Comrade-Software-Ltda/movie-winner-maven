package com.comrades.app.core.movie.usecases;

import com.comrades.app.domain.models.Movie;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProcessIntervalBetweenWinnerDto {

    private Integer yearDifference;

    private Integer previousWin;

    private Integer followingWin;

    private List<Movie> movies;
}
