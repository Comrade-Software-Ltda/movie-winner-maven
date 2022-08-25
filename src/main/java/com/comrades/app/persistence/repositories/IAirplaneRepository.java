package com.comrades.app.persistence.repositories;

import com.comrades.app.domain.models.Airplane;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IAirplaneRepository {
    List<Airplane> findAll();
    int save(Airplane movie);
    int edit(Airplane movie);
    int delete(UUID id);
    Optional<Airplane> findById(UUID id);
}
