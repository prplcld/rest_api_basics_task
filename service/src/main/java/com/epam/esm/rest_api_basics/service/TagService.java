package com.epam.esm.rest_api_basics.service;

import com.epam.esm.rest_api_basics.model.entity.Tag;

import java.util.List;

public interface TagService {
    Long addTag(Tag tag);

    List<Tag> getAllTags();

    Tag getById(Long id);

    void deleteTag(Long id);
}
