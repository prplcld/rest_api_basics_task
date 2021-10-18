package com.epam.esm.rest_api_basics.service.impl;

import com.epam.esm.rest_api_basics.model.dao.TagDao;
import com.epam.esm.rest_api_basics.model.entity.Tag;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

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

        when(tagDao.createTag(tag)).thenReturn(1L);
        tagServiceImpl.addTag(tag);
        verify(tagDao).createTag(tag);
    }

    @Test
    void testGetAllTags() {
        when(tagDao.getAllTags()).thenReturn(provideTagsList());

        tagServiceImpl.getAllTags();
        verify(tagDao).getAllTags();
    }

    @Test
    void testGetById() {
        Long tagId = 1L;

        when(tagDao.getTagById(tagId)).thenReturn(provideTagsList().get(1));

        Tag tag = tagServiceImpl.getById(tagId);
        verify(tagDao).getTagById(tagId);
        assertEquals(provideTagsList().get(1), tag);
    }

    @Test
    void testDeleteTag() {
        Long tagId = 1L;
        when(tagDao.deleteTag(tagId)).thenReturn(true);
        when(tagDao.deleteTagFromCertificates(tagId)).thenReturn(true);

        tagServiceImpl.deleteTag(tagId);
        verify(tagDao).deleteTag(tagId);
        verify(tagDao).deleteTagFromCertificates(tagId);
    }

    private List<Tag> provideTagsList() {
        Tag firstTag = new Tag();
        firstTag.setTagId(1L);
        firstTag.setName("tag1");

        Tag secondTag = new Tag();
        secondTag.setTagId(2L);
        secondTag.setName("tag2");

        Tag thirdTag = new Tag();
        thirdTag.setTagId(3L);
        thirdTag.setName("tag3");

        return new ArrayList<>() {{
            add(firstTag);
            add(secondTag);
            add(thirdTag);
        }};
    }
}