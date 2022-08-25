package com.comrades.app.domain.models;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table("movi_movie")
public class Movie {

    public Movie(UUID _id, Integer _year, String _title, String _studios, String _winner){
        id = _id;
        year = _year;
        title = _title;
        studios = _studios;
        winner = _winner;
    }

    public Movie(UUID _id, Integer _year, String _title, String _studios, String _producerName, String _winner){
        id = _id;
        year = _year;
        title = _title;
        studios = _studios;
        producerName = _producerName;
        winner = _winner;
    }

    @Id
    @Column("movi_uuid_movie")
    private UUID id;

    @Column("movi_nb_year")
    private Integer year;

    @Column("movi_tx_title")
    private String title;

    @Column("movi_tx_studios")
    private String studios;

    private List<Producer> producer;

    private String producerName;

    @Column("movi_tx_winner")
    private String winner;

}
