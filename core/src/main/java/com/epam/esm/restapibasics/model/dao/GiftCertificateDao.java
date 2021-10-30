package com.epam.esm.restapibasics.model.dao;

import com.epam.esm.restapibasics.model.entity.GiftCertificate;

import java.util.List;

public interface GiftCertificateDao {
    GiftCertificate getById(Long id);

    List<GiftCertificate> find(String tagName, String certificateName, String certificateDescription,
                               OrderType orderByName, OrderType orderByCreateDate);

    boolean delete(Long id);

    boolean update(GiftCertificate giftCertificate);

    Long create(GiftCertificate giftCertificate);

    boolean attachTag(Long certificateId, Long tagId);

    boolean deleteFromTags(Long id);
}
