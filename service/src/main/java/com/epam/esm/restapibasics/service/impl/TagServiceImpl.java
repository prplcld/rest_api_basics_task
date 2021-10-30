package com.epam.esm.restapibasics.service.impl;

import com.epam.esm.restapibasics.model.dao.TagDao;
import com.epam.esm.restapibasics.model.dao.exception.EntityNotFoundException;
import com.epam.esm.restapibasics.model.entity.Tag;
import com.epam.esm.restapibasics.service.TagService;
import com.epam.esm.restapibasics.service.dto.TagDto;
import com.epam.esm.restapibasics.service.exception.DaoResultException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TagServiceImpl implements TagService {

    private final TagDao tagDao;

    public TagServiceImpl(TagDao tagDao) {
        this.tagDao = tagDao;
    }

    /**
     * Adds new tag.
     *
     * @param tagDto tag object to be added.
     * @return tag id value.
     */
    public Long create(TagDto tagDto) {
        Tag tag = tagDto.toTag();
        return tagDao.create(tag);
    }

    /**
     * Gets all  tags.
     *
     * @return list of {@link TagDto}
     */
    public List<TagDto> getAll() {
        List<Tag> tags = tagDao.getTags();
        return tags.stream()
                .map(TagDto::fromTag)
                .collect(Collectors.toList());
    }

    /**
     * Gets tag by id.
     *
     * @param id tag id value.
     * @return {@link TagDto}
     */
    public TagDto getById(Long id) {
        Tag tag = tagDao.getById(id);
        return TagDto.fromTag(tag);
    }

    /**
     * Deletes tag by id.
     *
     * @param id tag id value.
     * @throws EntityNotFoundException
     */
    @Transactional(rollbackFor = Exception.class, timeout = 30)
    public void delete(Long id) {
        tagDao.deleteFromCertificates(id);
        if (!tagDao.delete(id)) {
            throw new EntityNotFoundException(id);
        }
    }

}
