package com.epam.esm.restapibasics.service;

import com.epam.esm.restapibasics.model.dao.Paginator;
import com.epam.esm.restapibasics.service.dto.TagDto;

import java.util.List;

public interface TagService {

    /**
     * Create a new tag.
     *
     * @param tagDto {@link TagDto} instance
     * @return {@link TagDto} object that represents created tag
     */
    TagDto create(TagDto tagDto);

    /**
     * Retrieve all tags.
     *
     * @param paginator {@link Paginator} object with pagination logic
     * @return list of {@link TagDto}
     */
    List<TagDto> getAll(Paginator paginator);

    /**
     * Retrieve tag by its unique id.
     *
     * @param id tag id
     * @return {@link TagDto} object
     */
    TagDto getById(Long id);

    /**
     * Delete an existing tag.
     *
     * @param id tag id
     */
    void delete(Long id);

    /**
     * Retrieve the most widely used tag of a user with the highest cost of all orders.
     *
     * @return {@link TagDto} object
     */
    TagDto findMostWidelyUsedTag();
}
