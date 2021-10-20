package com.epam.esm.restapibasics.service;

import com.epam.esm.restapibasics.service.dto.TagDto;

import java.util.List;

public interface TagService {
    Long create(TagDto tagDto);

    List<TagDto> getAll();

    TagDto getById(Long id);

    void delete(Long id);
}
