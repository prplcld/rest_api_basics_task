package com.epam.esm.restapibasics.service.impl;

import com.epam.esm.restapibasics.model.dao.Paginator;
import com.epam.esm.restapibasics.model.dao.TagDao;
import com.epam.esm.restapibasics.model.entity.Tag;
import com.epam.esm.restapibasics.service.TagService;
import com.epam.esm.restapibasics.service.dto.TagDto;
import com.epam.esm.restapibasics.service.dto.util.DtoMappingUtil;
import com.epam.esm.restapibasics.service.exception.EntityAlreadyExistsException;
import com.epam.esm.restapibasics.service.exception.EntityNotFoundException;
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


    @Transactional
    public TagDto create(TagDto tagDto) {
        Tag tag = DtoMappingUtil.mapToTag(tagDto);
        String name = tag.getName();
        if (tagDao.getByName(name).isPresent()) {
            throw new EntityAlreadyExistsException();
        }
        return DtoMappingUtil.mapToTagDto(tagDao.create(tag));
    }

    public List<TagDto> getAll(Paginator paginator) {
        return tagDao.getAll(paginator)
                .stream()
                .map(DtoMappingUtil::mapToTagDto)
                .collect(Collectors.toList());
    }


    public TagDto getById(Long id) {
        Optional<Tag> tag = tagDao.getById(id);
        return tag.map(DtoMappingUtil::mapToTagDto).orElseThrow(() -> new EntityNotFoundException(id, Tag.class));
    }


    @Transactional
    public void delete(Long id) {
        Tag tag = tagDao.getById(id)
                .orElseThrow(() -> new EntityNotFoundException(id, Tag.class));
        tagDao.delete(tag);
    }

    @Override
    public TagDto findMostWidelyUsedTag() {
        Optional<Tag> tag = tagDao.findMostWidelyUsedTag();
        return tag.map(DtoMappingUtil::mapToTagDto).orElseThrow(() -> new EntityNotFoundException(Tag.class));
    }
}
