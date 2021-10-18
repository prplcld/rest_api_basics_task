package com.epam.esm.rest_api_basics.service.impl;

import com.epam.esm.rest_api_basics.model.dao.TagDao;
import com.epam.esm.rest_api_basics.model.entity.Tag;
import com.epam.esm.rest_api_basics.service.TagService;
import com.epam.esm.rest_api_basics.service.exception.BadRequestException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TagServiceImpl implements TagService {

    private final TagDao tagDao;

    public TagServiceImpl(TagDao tagDao) {
        this.tagDao = tagDao;
    }

    /**
     * Adds new tag.
     *
     * @param tag tag object to be added.
     * @return tag id value.
     */
    public Long addTag(Tag tag) {
        return tagDao.createTag(tag);
    }

    /**
     * Gets all  tags.
     *
     * @return list of {@link Tag}
     */
    public List<Tag> getAllTags() {
        return tagDao.getAllTags();
    }

    /**
     * Gets tag by id.
     *
     * @param id tag id value.
     * @return {@link Tag}
     */
    public Tag getById(Long id) {
        return tagDao.getTagById(id);
    }

    /**
     * Deletes tag by id.
     *
     * @param id tag id value.
     * @throws BadRequestException
     */
    @Transactional(rollbackFor = Exception.class, timeout = 30)
    public void deleteTag(Long id) {
        tagDao.deleteTagFromCertificates(id);
        if (!tagDao.deleteTag(id)) {
            throw new BadRequestException();
        }
    }

}
