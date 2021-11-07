package com.epam.esm.restapibasics.model.dao;

import com.epam.esm.restapibasics.model.entity.GiftCertificate;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface GiftCertificateDao {
    Optional<GiftCertificate> getById(Long id);

    List<GiftCertificate> find(Paginator paginator, Map<String, SearchParameter> parameters);

    void delete(GiftCertificate giftCertificate);

    GiftCertificate update(GiftCertificate giftCertificate);

    GiftCertificate create(GiftCertificate giftCertificate);
}
