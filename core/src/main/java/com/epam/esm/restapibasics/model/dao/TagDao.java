package com.epam.esm.restapibasics.model.dao;

import com.epam.esm.restapibasics.model.entity.Tag;

import java.util.List;
import java.util.Optional;

public interface TagDao {
    List<Tag> getAll(Paginator paginator);

    Optional<Tag> getById(Long id);

    Long create(Tag tag);

    void delete(Long id);

    Optional<Tag> getByName(String name);
}
