package com.epam.esm.rest_api_basics.service.impl;

import com.epam.esm.rest_api_basics.model.dao.GiftCertificateDao;
import com.epam.esm.rest_api_basics.model.dao.TagDao;
import com.epam.esm.rest_api_basics.model.dao.exception.NoDataFoundException;
import com.epam.esm.rest_api_basics.model.entity.GiftCertificate;
import com.epam.esm.rest_api_basics.model.entity.Tag;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.time.ZoneOffset.UTC;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GiftCertificateServiceImplTest {

    @InjectMocks
    private GiftCertificateServiceImpl giftCertificateService;

    @Mock
    private GiftCertificateDao giftCertificateDao;

    @Mock
    private TagDao tagDao;

    private static final LocalDateTime LOCAL_DATE_TIME = LocalDateTime.now(UTC);

    @BeforeAll
    static void setUp() {
        MockitoAnnotations.openMocks(GiftCertificateServiceImplTest.class);
    }


    @Test
    void getAllCertificates() {
        Long id = 1L;
        when(giftCertificateDao.getAllGiftCertificates()).thenReturn(provideCertificates());
        when(tagDao.getTagsByCertificateId(id)).thenReturn(provideTagsInCertificate().get(1L));

        giftCertificateService.getAllCertificates();
        verify(giftCertificateDao).getAllGiftCertificates();
        verify(tagDao).getTagsByCertificateId(id);
    }

    @Test
    void getCertificateById() {
        Long id = 1L;
        when(giftCertificateDao.getGiftCertificateById(id)).thenReturn(provideCertificates().get(id.intValue()));

        when(tagDao.getTagsByCertificateId(id+1)).thenReturn(provideTagsInCertificate().get(2L));


        GiftCertificate actual = giftCertificateService.getCertificateById(id);
        GiftCertificate expected = provideGiftCertificatesWithTags().get(1);

        assertEquals(expected, actual);
    }

    @Test
    void getCertificatesByPartOfNameOrDescription() {
        Long id = 1L;
        when(giftCertificateService.getAllCertificates()).thenReturn(provideGiftCertificatesWithTags());
        when(tagDao.getTagsByCertificateId(id)).thenReturn(provideTagsInCertificate().get(1L));
        List<GiftCertificate> actual = giftCertificateService.getCertificatesByPartOfNameOrDescription("1");

        List<GiftCertificate> expected = new ArrayList<>() {
            {
                add(provideGiftCertificatesWithTags().get(0));
            }

        };

        assertEquals(expected, actual);
    }

    @Test
    void deleteCertificate() {
        Long id = 1L;
        when(giftCertificateDao.deleteGiftCertificate(id)).thenReturn(true);
        when(giftCertificateDao.deleteCertificateFromTags(id)).thenReturn(true);

        giftCertificateService.deleteCertificate(id);
        verify(giftCertificateDao).deleteGiftCertificate(id);
        verify(giftCertificateDao).deleteCertificateFromTags(id);
    }

    @Test
    void updateGiftCertificate() throws NoDataFoundException {
        GiftCertificate giftCertificate = provideGiftCertificatesWithTags().get(1);
        Tag tag = giftCertificate.getTags().get(0);
        Tag tag1 = giftCertificate.getTags().get(1);

        when(giftCertificateDao.updateGiftCertificate(giftCertificate)).thenReturn(true);
        when(tagDao.getTagByName(tag.getName())).thenReturn(tag);
        when(tagDao.getTagByName(tag1.getName())).thenReturn(tag1);
        when(giftCertificateDao.addTagToCertificate(giftCertificate.getGiftCertificateId(), tag.getTagId())).thenReturn(true);
        when(giftCertificateDao.addTagToCertificate(giftCertificate.getGiftCertificateId(), tag1.getTagId())).thenReturn(true);
        giftCertificateService.updateGiftCertificate(giftCertificate);

        verify(giftCertificateDao).updateGiftCertificate(giftCertificate);
    }

    @Test
    void sortGiftCertificatesByDate() {
        when(giftCertificateService.getAllCertificates()).thenReturn(provideCertificates());
        List<GiftCertificate> actual = giftCertificateService.sortGiftCertificatesByDate(true);

        List<GiftCertificate> expected = provideCertificates();

        assertEquals(expected, actual);
    }

    @Test
    void sortGiftCertificateByName() {
        Long id = 1L;
        when(giftCertificateService.getAllCertificates()).thenReturn(provideCertificates());
        when(tagDao.getTagsByCertificateId(id)).thenReturn(provideTagsInCertificate().get(1L));
        when(tagDao.getTagsByCertificateId(id+1)).thenReturn(provideTagsInCertificate().get(2L));
        when(tagDao.getTagsByCertificateId(id+2)).thenReturn(provideTagsInCertificate().get(3L));
        List<GiftCertificate> actual = giftCertificateService.sortGiftCertificateByName(true);

        List<GiftCertificate> expected = new ArrayList<>() {
            {
                add(provideGiftCertificatesWithTags().get(2));
                add(provideGiftCertificatesWithTags().get(1));
                add(provideGiftCertificatesWithTags().get(0));
            }

        };

        assertEquals(expected, actual);
    }

    List<GiftCertificate> provideGiftCertificatesWithTags() {
        List<GiftCertificate> giftCertificates = provideCertificates();
        for (GiftCertificate g : giftCertificates) {
            g.setTags(provideTagsInCertificate().get(g.getGiftCertificateId()));
        }

        return giftCertificates;
    }

    Map<Long, List<Tag>> provideTagsInCertificate() {
        Map<Long, List<Tag>> tagsInCertificate = new HashMap<>();
        Tag tag = new Tag();
        tag.setTagId(1L);
        tag.setName("1");

        List<Tag> tags  = new ArrayList<>();
        tags.add(tag);

        tagsInCertificate.put(1L, tags);

        tags = new ArrayList<>();
        tags.add(tag);
        tag = new Tag();
        tag.setTagId(2L);
        tag.setName("2");
        tags.add(tag);

        tagsInCertificate.put(2L, tags);

        tagsInCertificate.put(3L, new ArrayList<>());

        return tagsInCertificate;
    }

    List<GiftCertificate> provideCertificates() {
        List<GiftCertificate> giftCertificates = new ArrayList<>();
        GiftCertificate giftCertificate = new GiftCertificate();
        giftCertificate.setGiftCertificateId(1L);
        giftCertificate.setName("1");
        giftCertificate.setDescription("1");
        giftCertificate.setPrice(new BigDecimal(1));
        giftCertificate.setDuration(1);
        giftCertificate.setCreateDate(LOCAL_DATE_TIME);
        giftCertificate.setLastUpdateDate(LOCAL_DATE_TIME);
        giftCertificates.add(giftCertificate);

        giftCertificate = new GiftCertificate();
        giftCertificate.setGiftCertificateId(2L);
        giftCertificate.setName("2");
        giftCertificate.setDescription("2");
        giftCertificate.setPrice(new BigDecimal(2));
        giftCertificate.setDuration(2);
        giftCertificate.setCreateDate(LOCAL_DATE_TIME);
        giftCertificate.setLastUpdateDate(LOCAL_DATE_TIME);
        giftCertificates.add(giftCertificate);

        giftCertificate = new GiftCertificate();
        giftCertificate.setGiftCertificateId(3L);
        giftCertificate.setName("3");
        giftCertificate.setDescription("3");
        giftCertificate.setPrice(new BigDecimal(3));
        giftCertificate.setDuration(3);
        giftCertificate.setCreateDate(LOCAL_DATE_TIME);
        giftCertificate.setLastUpdateDate(LOCAL_DATE_TIME);
        giftCertificates.add(giftCertificate);
        return giftCertificates;
    }
}