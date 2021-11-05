package com.epam.esm.restapibasics.service;

import com.epam.esm.restapibasics.model.dao.Paginator;
import com.epam.esm.restapibasics.model.dao.SearchParameter;
import com.epam.esm.restapibasics.service.dto.GiftCertificateDto;

import java.util.List;
import java.util.Map;

public interface GiftCertificateService {
    GiftCertificateDto create(GiftCertificateDto giftCertificateDto);

    List<GiftCertificateDto> getAll(Paginator paginator, Map<String, SearchParameter> parameters);

    GiftCertificateDto getById(Long id);

    void delete(Long id);

    void update(GiftCertificateDto giftCertificateDto);
}
