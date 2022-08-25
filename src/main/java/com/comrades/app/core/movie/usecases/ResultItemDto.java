package com.comrades.app.core.movie.usecases;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ResultItemDto {

    ResultItemDto(String _producer, Integer _interval, Integer _previousWin, Integer _followingWin){
        producer = _producer;
        interval = _interval;
        previousWin = _previousWin;
        followingWin = _followingWin;
    }

    private String producer;
    private Integer interval;
    private Integer previousWin;
    private Integer followingWin;
}
