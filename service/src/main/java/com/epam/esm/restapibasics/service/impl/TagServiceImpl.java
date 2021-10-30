package com.epam.esm.restapibasics.service.impl;

import com.epam.esm.restapibasics.model.dao.Paginator;
import com.epam.esm.restapibasics.model.dao.TagDao;
import com.epam.esm.restapibasics.model.dao.exception.EntityNotFoundException;
import com.epam.esm.restapibasics.model.entity.Tag;
import com.epam.esm.restapibasics.service.TagService;
import com.epam.esm.restapibasics.service.dto.TagDto;
import com.epam.esm.restapibasics.service.dto.util.DtoMappingUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
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
    public List<TagDto> getAll(Paginator paginator) {
       return tagDao.getAll(paginator)
               .stream()
               .map(DtoMappingUtil::mapFromTag)
               .collect(Collectors.toList());
    }

    /**
     * Gets tag by id.
     *
     * @param id tag id value.
     * @return {@link TagDto}
     */
    public TagDto getById(Long id) {
        Optional<Tag> tag = tagDao.getById(id);
        return tag.map(DtoMappingUtil::mapFromTag).orElseThrow(() -> new EntityNotFoundException(id));
    }

    /**
     * Deletes tag by id.
     *
     * @param id tag id value.
     * @throws EntityNotFoundException
     */
    @Transactional(rollbackFor = Exception.class, timeout = 30)
    public void delete(Long id) {
        tagDao.delete(id);
    }

}
