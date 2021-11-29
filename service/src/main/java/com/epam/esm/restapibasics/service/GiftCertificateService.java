package com.epam.esm.restapibasics.service;

import com.epam.esm.restapibasics.model.dao.Paginator;
import com.epam.esm.restapibasics.model.dao.SearchParameter;
import com.epam.esm.restapibasics.service.dto.GiftCertificateDto;

import java.util.List;
import java.util.Map;

public interface GiftCertificateService {

    /**
     * Create a new certificate.
     *
     * @return {@link GiftCertificateDto} object that represents created
     */
    GiftCertificateDto create(GiftCertificateDto giftCertificateDto);

    /**
     * Retrieve certificates according to specified parameters encapsulated in DTO object.
     * All parameters are optional, so if they are not present, all certificates will be retrieved.
     *
     * @return list of {@link GiftCertificateDto}
     */
    List<GiftCertificateDto> getAll(Paginator paginator, Map<String, SearchParameter> parameters);

    /**
     * Retrieve certificate by its unique id.
     *
     * @param id certificate id
     * @return {@link GiftCertificateDto} object
     */
    GiftCertificateDto getById(Long id);

    /**
     * Delete an existing certificate.
     *
     * @param id certificate id
     */
    void delete(Long id);

    /**
     * Update an existing certificate.
     *
     * @param giftCertificateDto {@link GiftCertificateDto} instance
     * @return updated {@link GiftCertificateDto} object
     */
    GiftCertificateDto update(GiftCertificateDto giftCertificateDto);
}
