package com.epam.esm.rest_api_basics.model.dao;

import com.epam.esm.rest_api_basics.model.entity.GiftCertificate;

import java.util.List;

public interface GiftCertificateDao {
    GiftCertificate getGiftCertificateById(Long id);

    List<GiftCertificate> getAllGiftCertificates();

    boolean deleteGiftCertificate(Long id);

    boolean updateGiftCertificate(GiftCertificate giftCertificate);

    Long addGiftCertificate(GiftCertificate giftCertificate);

    boolean addTagToCertificate(Long certificateId, Long tagId);

    boolean deleteCertificateFromTags(Long id);
}
