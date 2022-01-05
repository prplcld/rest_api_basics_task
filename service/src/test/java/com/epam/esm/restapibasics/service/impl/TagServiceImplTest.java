package com.epam.esm.restapibasics.service.impl;

import com.epam.esm.restapibasics.model.dao.Paginator;
import com.epam.esm.restapibasics.model.dao.TagDao;
import com.epam.esm.restapibasics.model.entity.Tag;
import com.epam.esm.restapibasics.service.dto.TagDto;
import com.epam.esm.restapibasics.service.exception.EntityAlreadyExistsException;
import com.epam.esm.restapibasics.service.exception.EntityNotFoundException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TagServiceImplTest {

    @InjectMocks
    private TagServiceImpl tagService;

    @Mock
    private TagDao tagDao;

    @BeforeAll
    static void setUp() {
        MockitoAnnotations.openMocks(GiftCertificateServiceImplTest.class);
    }

    @Test
    void testFindAll() {
        Paginator paginator = new Paginator(null, null);
        when(tagDao.getAll(paginator)).thenReturn(provideTagsList());

        List<TagDto> expectedDtoList = provideTagDtoList();
        List<TagDto> actualDtoList = tagService.getAll(paginator);

        assertEquals(expectedDtoList, actualDtoList);
    }

    @Test
    void testFindById() {
        long tagId = 1;
        when(tagDao.getById(tagId)).thenReturn(Optional.of(provideTag()));

        TagDto expectedDto = provideTagDto();
        TagDto actualDto = tagService.getById(tagId);

        assertEquals(expectedDto, actualDto);
    }

    @Test
    void testFindByIdWhenTagNotFound() {
        long tagId = 1;
        when(tagDao.getById(tagId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> tagService.getById(tagId));
    }

    @Test
    void testCreate() {
        TagDto tagDto = provideTagDto();
        Tag tag = provideTag();

        when(tagDao.getByName(tagDto.getName())).thenReturn(Optional.empty());
        when(tagDao.create(tag)).thenReturn(tag);

        tagService.create(tagDto);

        verify(tagDao).getByName(tag.getName());
        verify(tagDao).create(tag);
    }

    @Test
    void testCreateWhenTagAlreadyExists() {
        TagDto tagDto = provideTagDto();
        Tag tag = provideTag();

        when(tagDao.getByName(tagDto.getName())).thenReturn(Optional.of(tag));
        assertThrows(EntityAlreadyExistsException.class, () -> tagService.create(tagDto));
    }

    @Test
    void testDelete() {
        Tag tag = provideTag();
        Long tagId = 1L;
        when(tagDao.getById(tagId)).thenReturn(Optional.of(tag));

        tagService.delete(tagId);

        verify(tagDao).delete(tag);
    }

    @Test
    void testDeleteWhenTagNotFound() {
        Long tagId = 1L;
        when(tagDao.getById(tagId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> tagService.delete(tagId));
    }


    private Tag provideTag() {
        Tag tag = new Tag();
        tag.setId(1L);
        tag.setName("tag");

        return tag;
    }

    private TagDto provideTagDto() {
        TagDto tagDto = new TagDto();
        tagDto.setId(1L);
        tagDto.setName("tag");

        return tagDto;
    }

    private List<Tag> provideTagsList() {
        Tag firstTag = new Tag();
        firstTag.setId(1L);
        firstTag.setName("tag1");

        Tag secondTag = new Tag();
        secondTag.setName("tag2");

        Tag thirdTag = new Tag();
        thirdTag.setName("tag3");

        return new ArrayList<>() {{
            add(firstTag);
            add(secondTag);
            add(thirdTag);
        }};
    }

    private List<TagDto> provideTagDtoList() {
        TagDto firstTagDto = new TagDto();
        firstTagDto.setId(1L);
        firstTagDto.setName("tag1");

        TagDto secondTagDto = new TagDto();
        secondTagDto.setName("tag2");

        TagDto thirdTagDto = new TagDto();
        thirdTagDto.setName("tag3");

        return new ArrayList<>() {{
            add(firstTagDto);
            add(secondTagDto);
            add(thirdTagDto);
        }};
    }
}