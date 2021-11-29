package com.epam.esm.restapibasics.model.dao;

import com.epam.esm.restapibasics.model.entity.Tag;

import java.util.List;
import java.util.Optional;

public interface TagDao {
    /**
     * Retrieve all tags from storage.
     *
     * @param paginator {@link Paginator} object with pagination logic
     * @return list of {@link Tag}
     */
    List<Tag> getAll(Paginator paginator);

    /**
     * Retrieve tag by its unique id.
     *
     * @param id tag id
     * @return {@link Tag} wrapped by {@link Optional}
     */
    Optional<Tag> getById(Long id);

    /**
     * Create a new tag in the storage.
     *
     * @param tag {@link Tag} instance
     * @return created {@link Tag}
     */
    Tag create(Tag tag);

    /**
     * Delete an existing tag from the storage.
     *
     */
    void delete(Long id);

    /**
     * Retrieve tag by its unique name.
     *
     * @param name tag name
     * @return {@link Tag} wrapped by {@link Optional}
     */
    Optional<Tag> getByName(String name);

    /**
     * Retrieve the most widely used tag of a user with the highest cost of all orders.
     *
     * @return {@link Tag} wrapped by {@link Optional}
     */
    Optional<Tag> findMostWidelyUsedTag();
}
