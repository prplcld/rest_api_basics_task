package com.epam.esm.restapibasics.service;

import com.epam.esm.restapibasics.model.dao.Paginator;
import com.epam.esm.restapibasics.service.dto.TagDto;

import java.util.List;

public interface TagService {
    Long create(TagDto tagDto);

    List<TagDto> getAll(Paginator paginator);

    TagDto getById(Long id);

    void delete(Long id);
}
