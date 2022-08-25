package com.comrades.app.application.services.movie.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonPropertyOrder({
        "year",
        "title",
        "studios",
        "producers",
        "winner"
})
public class MovieDto {

    private UUID id;

    @JsonProperty("year")
    private String year;

    @JsonProperty("title")
    private String title;

    @JsonProperty("studios")
    private String studios;

    @JsonProperty("producers")
    private String producers;

    @JsonProperty("winner")
    private String winner;
}
