package com.epam.esm.restapibasics.service.impl;

import com.epam.esm.restapibasics.model.dao.TagDao;
import com.epam.esm.restapibasics.model.entity.Tag;
import com.epam.esm.restapibasics.service.dto.TagDto;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TagServiceImplTest {

    @InjectMocks
    private TagServiceImpl tagServiceImpl;

    @BeforeAll
    static void setUp() {
        MockitoAnnotations.openMocks(GiftCertificateServiceImplTest.class);
    }

    @Mock
    private TagDao tagDao;

    @Test
    void testAddTag() {
        Tag tag = provideTagsList().get(1);
        TagDto tagDto = TagDto.fromTag(tag);
        when(tagDao.create(tag)).thenReturn(1L);
        tagServiceImpl.create(tagDto);
        verify(tagDao).create(tag);
    }

    @Test
    void testGetAllTags() {
//        when(tagDao.getTags()).thenReturn(provideTagsList());
//
//        tagServiceImpl.getAll();
//        verify(tagDao).getTags();
    }

    @Test
    void testGetById() {
//        Long tagId = 1L;
//
//        when(tagDao.getById(tagId)).thenReturn(provideTagsList().get(1));
//
//        TagDto tag = tagServiceImpl.getById(tagId);
//        verify(tagDao).getById(tagId);
//        assertEquals(TagDto.fromTag(provideTagsList().get(1)), tag);
    }

    @Test
    void testDeleteTag() {
//        Long tagId = 1L;
//        when(tagDao.delete(tagId)).thenReturn(true);
//        when(tagDao.deleteFromCertificates(tagId)).thenReturn(true);
//
//        tagServiceImpl.delete(tagId);
//        verify(tagDao).delete(tagId);
//        verify(tagDao).deleteFromCertificates(tagId);
    }

    private List<Tag> provideTagsList() {
        Tag firstTag = new Tag();
        firstTag.setId(1L);
        firstTag.setName("tag1");

        Tag secondTag = new Tag();
        secondTag.setId(2L);
        secondTag.setName("tag2");

        Tag thirdTag = new Tag();
        thirdTag.setId(3L);
        thirdTag.setName("tag3");

        return new ArrayList<>() {{
            add(firstTag);
            add(secondTag);
            add(thirdTag);
        }};
    }
}