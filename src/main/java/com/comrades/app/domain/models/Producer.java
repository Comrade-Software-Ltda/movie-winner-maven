package com.comrades.app.domain.models;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table("prod_producer")
public class Producer {

    @Id
    @With
    @Column("prod_uuid_producer")
    private UUID id;

    @Column("movi_uuid_movie")
    private UUID movieId;

    @Column("prod_tx_name")
    private String name;


}
