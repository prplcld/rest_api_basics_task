package com.epam.esm.rest_api_basics.model.dao.impl;

import com.epam.esm.rest_api_basics.model.dao.GiftCertificateDao;
import com.epam.esm.rest_api_basics.model.dao.TestProfileResolver;
import com.epam.esm.rest_api_basics.model.dao.config.DatabaseConfig;
import com.epam.esm.rest_api_basics.model.dao.exception.NoIdException;
import com.epam.esm.rest_api_basics.model.entity.GiftCertificate;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(SpringExtension.class)
@ComponentScan("com.epam.esm")
@ContextConfiguration(classes = DatabaseConfig.class)
@Transactional
@ActiveProfiles(resolver = TestProfileResolver.class)
@Sql(scripts = "classpath:schema/clear.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@Sql(scripts = {"classpath:schema/init.sql", "classpath:schema/init_data.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class GiftCertificateDaoImplTest {

    @Autowired
    private GiftCertificateDao giftCertificateDao;

    @Test
    void testAddGiftCertificateTest() {
        GiftCertificate giftCertificate = new GiftCertificate();
        giftCertificate.setName("abc");
        giftCertificate.setDescription("abc");
        giftCertificate.setDuration(10);
        giftCertificate.setPrice(new BigDecimal(10));
        Long id = giftCertificateDao.addGiftCertificate(giftCertificate);
        assertEquals(5, id);
    }

    @Test
    void testGetGiftCertificateById() {
        GiftCertificate expected = new GiftCertificate();
        expected.setGiftCertificateId(4L);
        expected.setName("certificate4");
        expected.setDescription("certificate4");
        expected.setPrice(new BigDecimal("1.00"));
        expected.setDuration(20);
        expected.setCreateDate(LocalDateTime.of(2021, 9, 25, 0, 0, 0));
        expected.setLastUpdateDate(LocalDateTime.of(2021, 9, 25, 0, 0, 0));
        GiftCertificate actual = giftCertificateDao.getGiftCertificateById(4L);

        assertEquals(expected, actual);
    }

    @Test
    void testGetAllGiftCertificates() {
        List<GiftCertificate> expected = provideGiftCertificates();
        List<GiftCertificate> actual = giftCertificateDao.getAllGiftCertificates();

        assertEquals(expected, actual);
    }

    @Test
    void testDeleteGiftCertificate() {
        List<GiftCertificate> expected = provideGiftCertificates();
        expected.remove(3);
        giftCertificateDao.deleteGiftCertificate(4L);

        List<GiftCertificate> actual = giftCertificateDao.getAllGiftCertificates();

        assertEquals(expected, actual);
    }

    @Test
    void testDeleteCertificateFromTags() {
        boolean actual = giftCertificateDao.deleteCertificateFromTags(1L);
        assertTrue(actual);
    }

    @Test
    void testUpdateGiftCertificate() {
        GiftCertificate giftCertificate = new GiftCertificate();
        giftCertificate.setGiftCertificateId(1L);
        giftCertificate.setName("123");

        boolean actual = giftCertificateDao.updateGiftCertificate(giftCertificate);

        assertTrue(actual);
    }

    @Test
    void testUpdateGiftCertificateThrowsException() {
        GiftCertificate giftCertificate = new GiftCertificate();
        giftCertificate.setName("123");

        assertThrows(NoIdException.class, () -> giftCertificateDao.updateGiftCertificate(giftCertificate));
    }

    @Test
    void testAddTagToCertificate() {
        boolean actual = giftCertificateDao.addTagToCertificate(1L, 2L);
        assertTrue(actual);
    }

    List<GiftCertificate> provideGiftCertificates() {
        List<GiftCertificate> giftCertificates = new ArrayList<>();
        GiftCertificate giftCertificate = new GiftCertificate();
        giftCertificate.setGiftCertificateId(1L);
        giftCertificate.setName("certificate1");
        giftCertificate.setDescription("certificate1");
        giftCertificate.setPrice(new BigDecimal("1.00"));
        giftCertificate.setDuration(1);
        giftCertificate.setCreateDate(LocalDateTime.of(2021, 9, 25, 0, 0, 0));
        giftCertificate.setLastUpdateDate(LocalDateTime.of(2021, 9, 25, 0, 0, 0));
        giftCertificates.add(giftCertificate);


        giftCertificate = new GiftCertificate();
        giftCertificate.setGiftCertificateId(2L);
        giftCertificate.setName("certificate2");
        giftCertificate.setDescription("certificate2");
        giftCertificate.setPrice(new BigDecimal("1.10"));
        giftCertificate.setDuration(2);
        giftCertificate.setCreateDate(LocalDateTime.of(2021, 9, 25, 0, 0, 0));
        giftCertificate.setLastUpdateDate(LocalDateTime.of(2021, 9, 25, 0, 0, 0));
        giftCertificates.add(giftCertificate);

        giftCertificate = new GiftCertificate();
        giftCertificate.setGiftCertificateId(3L);
        giftCertificate.setName("certificate3");
        giftCertificate.setDescription("certificate3");
        giftCertificate.setPrice(new BigDecimal("10.30"));
        giftCertificate.setDuration(10);
        giftCertificate.setCreateDate(LocalDateTime.of(2021, 9, 25, 0, 0, 0));
        giftCertificate.setLastUpdateDate(LocalDateTime.of(2021, 9, 25, 0, 0, 0));
        giftCertificates.add(giftCertificate);

        giftCertificate = new GiftCertificate();
        giftCertificate.setGiftCertificateId(4L);
        giftCertificate.setName("certificate4");
        giftCertificate.setDescription("certificate4");
        giftCertificate.setPrice(new BigDecimal("1.00"));
        giftCertificate.setDuration(20);
        giftCertificate.setCreateDate(LocalDateTime.of(2021, 9, 25, 0, 0, 0));
        giftCertificate.setLastUpdateDate(LocalDateTime.of(2021, 9, 25, 0, 0, 0));
        giftCertificates.add(giftCertificate);

        return giftCertificates;
    }
}