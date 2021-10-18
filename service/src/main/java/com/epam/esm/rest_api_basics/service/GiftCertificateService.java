package com.epam.esm.rest_api_basics.service;

import com.epam.esm.rest_api_basics.model.entity.GiftCertificate;

import java.util.List;

public interface GiftCertificateService {
    Long addGiftCertificate(GiftCertificate giftCertificate);

    List<GiftCertificate> getAllCertificates();

    GiftCertificate getCertificateById(Long id);

    List<GiftCertificate> getCertificatesByTagName(String name);

    List<GiftCertificate> getCertificatesByPartOfNameOrDescription(String text);

    void deleteCertificate(Long id);

    void updateGiftCertificate(GiftCertificate giftCertificate);

    List<GiftCertificate> sortGiftCertificatesByDate(boolean reversed);

    List<GiftCertificate> sortGiftCertificateByName(boolean reversed);
}
