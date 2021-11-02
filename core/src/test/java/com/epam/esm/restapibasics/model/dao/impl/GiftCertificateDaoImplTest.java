package com.epam.esm.restapibasics.model.dao.impl;

import com.epam.esm.restapibasics.model.dao.GiftCertificateDao;
import com.epam.esm.restapibasics.model.dao.OrderType;
import com.epam.esm.restapibasics.model.dao.TestProfileResolver;
import com.epam.esm.restapibasics.model.dao.config.DatabaseConfig;
import com.epam.esm.restapibasics.model.entity.GiftCertificate;
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

//    @Autowired
//    private GiftCertificateDao giftCertificateDao;
//
//    @Test
//    void testAddGiftCertificateTest() {
//        GiftCertificate giftCertificate = new GiftCertificate();
//        giftCertificate.setName("abc");
//        giftCertificate.setDescription("abc");
//        giftCertificate.setDuration(10);
//        giftCertificate.setPrice(new BigDecimal(10));
//        Long id = giftCertificateDao.create(giftCertificate);
//        assertEquals(5, id);
//    }
//
//    @Test
//    void testGetGiftCertificateById() {
//        GiftCertificate expected = new GiftCertificate();
//        expected.setId(4L);
//        expected.setName("certificate4");
//        expected.setDescription("certificate4");
//        expected.setPrice(new BigDecimal("1.00"));
//        expected.setDuration(20);
//        expected.setCreateDate(LocalDateTime.of(2021, 9, 25, 0, 0, 0));
//        expected.setLastUpdateDate(LocalDateTime.of(2021, 9, 25, 0, 0, 0));
//        GiftCertificate actual = giftCertificateDao.getById(4L);
//
//        assertEquals(expected, actual);
//    }
//
//    @Test
//    void testGetAllGiftCertificates() {
//        List<GiftCertificate> expected = provideGiftCertificates();
//        List<GiftCertificate> actual = giftCertificateDao.find(null, null, null, OrderType.ASC, null);
//
//        assertEquals(expected, actual);
//    }
//
//    @Test
//    void testDeleteGiftCertificate() {
//        List<GiftCertificate> expected = provideGiftCertificates();
//        expected.remove(3);
//        giftCertificateDao.delete(4L);
//
//        List<GiftCertificate> actual = giftCertificateDao.find(null, null, null, OrderType.ASC, null);
//
//        assertEquals(expected, actual);
//    }
//
//    @Test
//    void testDeleteCertificateFromTags() {
//        boolean actual = giftCertificateDao.deleteFromTags(1L);
//        assertTrue(actual);
//    }
//
//    @Test
//    void testUpdateGiftCertificate() {
//        GiftCertificate giftCertificate = new GiftCertificate();
//        giftCertificate.setId(1L);
//        giftCertificate.setName("123");
//
//        boolean actual = giftCertificateDao.update(giftCertificate);
//
//        assertTrue(actual);
//    }
//
//    @Test
//    void testAddTagToCertificate() {
//        boolean actual = giftCertificateDao.attachTag(1L, 2L);
//        assertTrue(actual);
//    }
//
//    List<GiftCertificate> provideGiftCertificates() {
//        List<GiftCertificate> giftCertificates = new ArrayList<>();
//        GiftCertificate giftCertificate = new GiftCertificate();
//        giftCertificate.setId(1L);
//        giftCertificate.setName("certificate1");
//        giftCertificate.setDescription("certificate1");
//        giftCertificate.setPrice(new BigDecimal("1.00"));
//        giftCertificate.setDuration(1);
//        giftCertificate.setCreateDate(LocalDateTime.of(2021, 9, 25, 0, 0, 0));
//        giftCertificate.setLastUpdateDate(LocalDateTime.of(2021, 9, 25, 0, 0, 0));
//        giftCertificates.add(giftCertificate);
//
//
//        giftCertificate = new GiftCertificate();
//        giftCertificate.setId(2L);
//        giftCertificate.setName("certificate2");
//        giftCertificate.setDescription("certificate2");
//        giftCertificate.setPrice(new BigDecimal("1.10"));
//        giftCertificate.setDuration(2);
//        giftCertificate.setCreateDate(LocalDateTime.of(2021, 9, 25, 0, 0, 0));
//        giftCertificate.setLastUpdateDate(LocalDateTime.of(2021, 9, 25, 0, 0, 0));
//        giftCertificates.add(giftCertificate);
//
//        giftCertificate = new GiftCertificate();
//        giftCertificate.setId(3L);
//        giftCertificate.setName("certificate3");
//        giftCertificate.setDescription("certificate3");
//        giftCertificate.setPrice(new BigDecimal("10.30"));
//        giftCertificate.setDuration(10);
//        giftCertificate.setCreateDate(LocalDateTime.of(2021, 9, 25, 0, 0, 0));
//        giftCertificate.setLastUpdateDate(LocalDateTime.of(2021, 9, 25, 0, 0, 0));
//        giftCertificates.add(giftCertificate);
//
//        giftCertificate = new GiftCertificate();
//        giftCertificate.setId(4L);
//        giftCertificate.setName("certificate4");
//        giftCertificate.setDescription("certificate4");
//        giftCertificate.setPrice(new BigDecimal("1.00"));
//        giftCertificate.setDuration(20);
//        giftCertificate.setCreateDate(LocalDateTime.of(2021, 9, 25, 0, 0, 0));
//        giftCertificate.setLastUpdateDate(LocalDateTime.of(2021, 9, 25, 0, 0, 0));
//        giftCertificates.add(giftCertificate);
//
//        return giftCertificates;
//    }
}