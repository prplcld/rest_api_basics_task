package com.epam.esm.rest_api_basics.model.dao;

import com.epam.esm.rest_api_basics.model.dao.exception.NoDataFoundException;
import com.epam.esm.rest_api_basics.model.entity.Tag;

import java.util.List;

public interface TagDao {
    List<Tag> getAllTags();

    Tag getTagById(Long id);

    Long createTag(Tag tag);

    boolean deleteTag(Long id);

    Tag getTagByName(String name) throws NoDataFoundException;

    List<Tag> getTagsByCertificateId(Long id);

    boolean deleteTagFromCertificates(Long id);

}
