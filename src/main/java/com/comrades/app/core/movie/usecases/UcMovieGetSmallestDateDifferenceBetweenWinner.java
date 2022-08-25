package com.comrades.app.core.movie.usecases;

import com.comrades.app.core.bases.UseCase;
import com.comrades.app.domain.models.Movie;
import com.comrades.app.persistence.repositories.MovieRepository;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;


@Getter
@Setter
public class UcMovieGetSmallestDateDifferenceBetweenWinner extends UseCase<ResultDto> {

    @Autowired
    private MovieRepository _movieRepository;


    public UcMovieGetSmallestDateDifferenceBetweenWinner() {
        super();
    }

    @Override
    protected ResultDto execute() throws Exception {
        var teste = _movieRepository.findMovieFromProducerWithMoreThanOrEqualTwoWinners();

        var moviesByStudio = teste.stream().collect(groupingBy(x -> x.getProducerName()));
        ArrayList<ProcessIntervalBetweenWinnerDto> processIntervalBetweenWinner = getProcessIntervalBetweenWinnerDtos(moviesByStudio);

        var minSpamNoConsecutiveWinner = processIntervalBetweenWinner.stream().min((o1, o2) -> o1.getYearDifference().compareTo(o2.getYearDifference())).get();
        var maxSpamNoConsecutiveWinner = processIntervalBetweenWinner.stream().max((o1, o2) -> o1.getYearDifference().compareTo(o2.getYearDifference())).get();

        return new ResultDto(minSpamNoConsecutiveWinner, maxSpamNoConsecutiveWinner);
    }

    private ArrayList<ProcessIntervalBetweenWinnerDto> getProcessIntervalBetweenWinnerDtos(Map<String, List<Movie>> moviesByStudio) {
        var processIntervalBetweenWinner = new ArrayList<ProcessIntervalBetweenWinnerDto>();
        for (var pair : moviesByStudio.entrySet()) {

            var tes = pair.getValue().stream().sorted((o1, o2) -> o1.getYear().compareTo(o2.getYear())).collect(Collectors.toList());
            for (int i = 0; i < tes.size() - 1; i++) {
                calculateIntervals(processIntervalBetweenWinner, tes, i);
            }

        }
        return processIntervalBetweenWinner;
    }

    private void calculateIntervals(ArrayList<ProcessIntervalBetweenWinnerDto> processIntervalBetweenWinner, List<Movie> tes, int i) {
        var previousWin = 0;
        var followingWin = 0;
        List<Movie> aux = new ArrayList<>();
        aux.add(tes.get(i));
        aux.add(tes.get(i + 1));
        var yearInterval = tes.get(i + 1).getYear() - tes.get(i).getYear();

        if ((i + 2) < (tes.size())) {
            followingWin = tes.get(i + 2).getYear();
        }

        if ((i + -1) > 0) {
            previousWin = tes.get(i - 1).getYear();
        }

        processIntervalBetweenWinner.add(new ProcessIntervalBetweenWinnerDto(yearInterval, previousWin, followingWin, aux));
    }


}


