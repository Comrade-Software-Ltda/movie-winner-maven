create table movi_movie(
    movi_uuid_movie uuid ,
    movi_nb_year  numeric ,
    movi_tx_title    varchar(1024),
    movi_tx_studios    varchar(1024),
    movi_tx_winner    varchar(1024),
    constraint pk_movi_movie primary key ( movi_uuid_movie )
);

create table prod_producer(
    prod_uuid_producer uuid ,
    movi_uuid_movie uuid ,
    prod_tx_name  varchar(1024),
    CONSTRAINT fk_movi_movie_prod_producer FOREIGN KEY (movi_uuid_movie) REFERENCES movi_movie(movi_uuid_movie),
    constraint pk_prod_producer primary key ( prod_uuid_producer )
);