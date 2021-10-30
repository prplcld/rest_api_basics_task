package com.epam.esm.restapibasics.service;

import com.epam.esm.restapibasics.service.dto.GiftCertificateDto;
import com.epam.esm.restapibasics.service.dto.SearchParamsModelDto;

import java.util.List;

public interface GiftCertificateService {
    Long create(GiftCertificateDto giftCertificateDto);

    List<GiftCertificateDto> getAll(SearchParamsModelDto searchParamsModelDto);

    GiftCertificateDto getById(Long id);

    void delete(Long id);

    void update(GiftCertificateDto giftCertificateDto);

}
