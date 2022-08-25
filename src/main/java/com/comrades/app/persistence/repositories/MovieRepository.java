package com.comrades.app.persistence.repositories;

import com.comrades.app.domain.models.Movie;
import com.comrades.app.domain.models.Producer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.beans.Statement;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class MovieRepository implements IMovieRepository {

    private final JdbcTemplate jdbcTemplate;

    public MovieRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Movie> findAll() {
        var sql = """
                SELECT movi_uuid_movie, movi_nb_year, movi_tx_title, movi_tx_studios, movi_tx_winner
                FROM movi_movie;
                """;
        return jdbcTemplate.query(sql, new MovieRowMapper());
    }

    @Override
    public int save(Movie movie) {
        try {
            var sql = """
                    INSERT INTO movi_movie(movi_uuid_movie, movi_nb_year, movi_tx_title, movi_tx_studios, movi_tx_winner)
                    VALUES (?, ?, ?, ?, ?);
                    """;
            return jdbcTemplate.update(
                    sql,
                    movie.getId(), movie.getYear(), movie.getTitle(), movie.getStudios(),
                    movie.getWinner()

            );
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
            throw ex;
        }
    }

    public int[] saveBatch(List<Movie> movies) {

        try {
            deleteAllProducers();
            deleteAllMovies();

            var sql = """
                    INSERT INTO movi_movie(movi_uuid_movie, movi_nb_year, movi_tx_title, movi_tx_studios,
                    movi_tx_winner)
                    VALUES (?, ?, ?, ?, ?);
                    """;

            return this.jdbcTemplate.batchUpdate(
                    sql,
                    new BatchPreparedStatementSetter() {
                        public void setValues(PreparedStatement ps, int i)
                                throws SQLException {
                            ps.setObject(1, movies.get(i).getId());
                            ps.setInt(2, movies.get(i).getYear());
                            ps.setString(3, movies.get(i).getTitle());
                            ps.setString(4, movies.get(i).getStudios());
                            ps.setString(5, movies.get(i).getWinner());
                        }

                        public int getBatchSize() {
                            return movies.size();
                        }
                    });
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
            throw ex;
        }
    }

    public int[] saveProducers(List<Producer> producers) {

        try {


            var sql = """
                    INSERT INTO prod_producer(prod_uuid_producer, movi_uuid_movie, prod_tx_name)
                    VALUES (?, ?, ?);
                    """;

            return this.jdbcTemplate.batchUpdate(
                    sql,
                    new BatchPreparedStatementSetter() {
                        public void setValues(PreparedStatement ps, int i)
                                throws SQLException {
                            ps.setObject(1, producers.get(i).getId());
                            ps.setObject(2, producers.get(i).getMovieId());
                            ps.setString(3, producers.get(i).getName());
                        }

                        public int getBatchSize() {
                            return producers.size();
                        }
                    });
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
            throw ex;
        }
    }

    @Override
    public int edit(Movie movie) {
        try {
            var sql = """
                    update movi_movie set
                    movi_nb_year = ?,
                    movi_tx_title = ?,
                    movi_tx_studios = ?
                    where movi_uuid_movie = ?
                    """;
            return jdbcTemplate.update(
                    sql,
                    movie.getYear(), movie.getTitle(), movie.getStudios(), movie.getId()
            );
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
            throw ex;
        }
    }


    @Override
    public int delete(UUID id) {
        try {
            var sql = """
                    DELETE FROM movi_movie   
                    WHERE movi_uuid_movie = ?
                    """;
            return jdbcTemplate.update(sql, id);
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
            throw ex;
        }

    }

    @Override
    public int deleteAllMovies() {
        try {
            var sql = """
                    DELETE FROM movi_movie
                    """;
            return jdbcTemplate.update(sql);
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
            throw ex;
        }
    }

    @Override
    public int deleteAllProducers() {
        try {
            var sql = """
                    DELETE FROM prod_producer
                    """;
            return jdbcTemplate.update(sql);
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
            throw ex;
        }
    }

    @Override
    public Optional<Movie> findById(UUID id) {
        var sql = """
                SELECT movi_uuid_movie, movi_nb_year, movi_tx_title, movi_tx_studios, movi_tx_winner
                FROM movi_movie
                WHERE movi_uuid_movie = ?
                """;

        return jdbcTemplate.query(sql, new MovieRowMapper(), id)
                .stream()
                .findFirst();
    }

    @Override
    public List<Movie> findMovieFromProducerWithMoreThanOrEqualTwoWinners() {
        var sql = """
                    SELECT movi.movi_uuid_movie, movi.movi_nb_year, movi.movi_tx_title, movi.movi_tx_studios,
                     prod.prod_tx_name, movi.movi_tx_winner
                        FROM public.movi_movie movi
                        inner join public.prod_producer prod on prod.movi_uuid_movie = movi.movi_uuid_movie
                            WHERE movi.movi_tx_winner = 'yes' and
                            prod.prod_tx_name IN
                            (
                                SELECT prod.prod_tx_name
                                FROM public.movi_movie projection
                                inner join public.prod_producer prod on prod.movi_uuid_movie = projection.movi_uuid_movie
                                WHERE projection.movi_tx_winner = 'yes'
                                GROUP BY prod.prod_tx_name
                                HAVING count(prod.prod_tx_name) >= 2
                            )
                                """;
        return jdbcTemplate.query(sql, new MovieCustomRowMapper());
    }
}
