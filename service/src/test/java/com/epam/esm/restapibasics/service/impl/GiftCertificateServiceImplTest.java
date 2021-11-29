package com.epam.esm.restapibasics.service.impl;

import com.epam.esm.restapibasics.model.dao.impl.GiftCertificateDaoImpl;
import com.epam.esm.restapibasics.model.dao.impl.TagDaoImpl;
import com.epam.esm.restapibasics.model.entity.GiftCertificate;
import com.epam.esm.restapibasics.model.entity.Tag;
import com.epam.esm.restapibasics.service.dto.GiftCertificateDto;
import com.epam.esm.restapibasics.service.dto.TagDto;
import com.epam.esm.restapibasics.service.dto.util.DtoMappingUtil;
import com.epam.esm.restapibasics.service.exception.EntityNotFoundException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.time.ZoneOffset.UTC;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class GiftCertificateServiceImplTest {
    private static final LocalDateTime INITIAL_DATE = LocalDateTime.now(UTC);

    @InjectMocks
    private GiftCertificateServiceImpl certificateService;

    @Mock
    private GiftCertificateDaoImpl giftCertificateDao;

    @Mock
    private TagDaoImpl tagDao;


    @BeforeAll
    static void setUp() {
        MockitoAnnotations.openMocks(GiftCertificateServiceImplTest.class);
    }

    @Test
    void testCreate() {
        GiftCertificateDto expectedDto = provideCertificateDto();
        GiftCertificate certificate = provideCertificate();

        when(giftCertificateDao.create(any(GiftCertificate.class))).thenReturn(certificate);
        GiftCertificateDto actualDto = certificateService.create(expectedDto);


        verify(giftCertificateDao).create(certificate);
        assertEquals(expectedDto, actualDto);
    }

    @Test
    void testDelete() {
        GiftCertificate certificate = provideCertificate();
        long certificateId = 1;
        when(giftCertificateDao.getById(certificateId)).thenReturn(Optional.of(certificate));

        certificateService.delete(certificateId);
        verify(giftCertificateDao).delete(certificate);
    }

    @Test
    void testDeleteWhenCertificateNotFound() {
        long certificateId = 1;
        when(giftCertificateDao.getById(certificateId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> certificateService.delete(certificateId));
    }

    @Test
    void testFindById() {
        long certificateId = 1;
        GiftCertificate certificate = provideCertificate();
        GiftCertificateDto expectedCertificateDto = provideCertificateDto();
        when(giftCertificateDao.getById(certificateId)).thenReturn(Optional.of(certificate));

        GiftCertificateDto actualCertificateDto = certificateService.getById(certificateId);

        verify(giftCertificateDao).getById(certificateId);
        assertEquals(expectedCertificateDto, actualCertificateDto);
    }


    @Test
    void testFindByIdWhenCertificateNotFound() {
        long certificateId = 1;
        when(giftCertificateDao.getById(certificateId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> certificateService.getById(certificateId));
    }



    private GiftCertificate provideCertificate() {
        GiftCertificate certificate = new GiftCertificate();

        certificate.setId(1L);
        certificate.setName("certificate");
        certificate.setDescription("description");
        certificate.setPrice(BigDecimal.ONE);
        certificate.setDuration(1);
        certificate.setCreateDate(INITIAL_DATE);
        certificate.setLastUpdateDate(INITIAL_DATE);
        certificate.setTags(provideTags());

        return certificate;
    }

    private GiftCertificateDto provideCertificateDto() {
        GiftCertificateDto certificateDto = new GiftCertificateDto();

        certificateDto.setId(1L);
        certificateDto.setName("certificate");
        certificateDto.setDescription("description");
        certificateDto.setPrice(BigDecimal.ONE);
        certificateDto.setDuration(1);
        certificateDto.setCreateDate(INITIAL_DATE);
        certificateDto.setLastUpdateDate(INITIAL_DATE);

        certificateDto.setTags(provideTagDtos());

        return certificateDto;
    }



    private List<Tag> provideTags() {
        Tag firstTag = new Tag();
        firstTag.setId(1L);
        firstTag.setName("tag1");

        Tag secondTag = new Tag();
        secondTag.setId(2L);
        secondTag.setName("tag2");

        return new ArrayList<>() {{
            add(firstTag);
            add(secondTag);
        }};
    }

    private List<TagDto> provideTagDtos() {
        return new ArrayList<>() {{
            for(Tag t : provideTags()) {
                add(DtoMappingUtil.mapToTagDto(t));
            }
        }};
    }
}