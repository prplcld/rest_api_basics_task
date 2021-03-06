package com.epam.esm.restapibasics.model.dao;

import com.epam.esm.restapibasics.model.dao.config.DatabaseConfig;
import com.epam.esm.restapibasics.model.entity.GiftCertificate;
import com.epam.esm.restapibasics.model.entity.Tag;
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
import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ComponentScan("com.epam.esm")
@ContextConfiguration(classes = DatabaseConfig.class)
@ActiveProfiles(resolver = TestProfileResolver.class)
@Sql(scripts = "classpath:schema/clear.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@Sql(scripts = {"classpath:schema/init.sql", "classpath:schema/init_data.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class GiftCertificateDaoImplTest {

    @Autowired
    private GiftCertificateDao giftCertificateDao;


    @Test
    void testFindById() {
        Optional<GiftCertificate> certificate = giftCertificateDao.getById(1L);
        assertTrue(certificate.isPresent() && certificate.get().getId() == 1);
    }

    @Test
    void testFindByIdNotFound() {
        Optional<GiftCertificate> certificate = giftCertificateDao.getById(0L);
        assertTrue(certificate.isEmpty());
    }

    @Test
    @Transactional
    void testCreate() {
        LocalDateTime localDateTime = LocalDateTime.now();
        GiftCertificate giftCertificate = new GiftCertificate();
        giftCertificate.setName("123");
        giftCertificate.setDescription("123");
        giftCertificate.setDuration(10);
        giftCertificate.setCreateDate(localDateTime);
        giftCertificate.setLastUpdateDate(localDateTime);
        giftCertificate.setPrice(BigDecimal.ONE);
        List<Tag> tagList = new ArrayList<>();
        Tag tag = new Tag();
        tag.setName("tag");
        tagList.add(tag);
        giftCertificate.setTags(tagList);

        giftCertificateDao.create(giftCertificate);
        assertNull(giftCertificate.getId());
    }


    @Test
    void testSortByNameAscending() {
        Paginator paginator = new Paginator(1, 50);
        Map<String, SearchParameter> params = new HashMap<>();
        List<String> values = new ArrayList<>();
        values.add("ASC");
        SearchParameter searchParameter = new SearchParameter(SearchParameterType.ORDER_BY, values);
        params.put("name", searchParameter);
        List<GiftCertificate> actual = giftCertificateDao.find(paginator, params);
        List<GiftCertificate> expected = actual.stream()
                .sorted(Comparator.comparing(GiftCertificate::getName))
                .collect(Collectors.toList());

        assertEquals(expected, actual);
    }

    @Test
    void testSortByNameDescending() {
        Paginator paginator = new Paginator(1, 50);
        Map<String, SearchParameter> params = new HashMap<>();
        List<String> values = new ArrayList<>();
        values.add("DESC");
        SearchParameter searchParameter = new SearchParameter(SearchParameterType.ORDER_BY, values);
        params.put("name", searchParameter);
        List<GiftCertificate> actual = giftCertificateDao.find(paginator, params);
        List<GiftCertificate> expected = actual.stream()
                .sorted(Collections.reverseOrder(Comparator.comparing(GiftCertificate::getName)))
                .collect(Collectors.toList());

        assertEquals(expected, actual);
    }

    @Test
    void testSortByCreateDateAscending() {
        Paginator paginator = new Paginator(1, 50);
        Map<String, SearchParameter> params = new HashMap<>();
        List<String> values = new ArrayList<>();
        values.add("ASC");
        SearchParameter searchParameter = new SearchParameter(SearchParameterType.ORDER_BY, values);
        params.put("createDate", searchParameter);
        List<GiftCertificate> actual = giftCertificateDao.find(paginator, params);
        List<GiftCertificate> expected = actual.stream()
                .sorted(Comparator.comparing(GiftCertificate::getCreateDate))
                .collect(Collectors.toList());

        assertEquals(expected, actual);
    }

    @Test
    void testSortByCreateDateDescending() {
        Paginator paginator = new Paginator(1, 50);
        Map<String, SearchParameter> params = new HashMap<>();
        List<String> values = new ArrayList<>();
        values.add("DESC");
        SearchParameter searchParameter = new SearchParameter(SearchParameterType.ORDER_BY, values);
        params.put("createDate", searchParameter);
        List<GiftCertificate> actual = giftCertificateDao.find(paginator, params);
        List<GiftCertificate> expected = actual.stream()
                .sorted(Collections.reverseOrder(Comparator.comparing(GiftCertificate::getCreateDate)))
                .collect(Collectors.toList());

        assertEquals(expected, actual);
    }
}
