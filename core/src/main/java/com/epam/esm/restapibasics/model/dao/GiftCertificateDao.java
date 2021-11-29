package com.epam.esm.restapibasics.model.dao;

import com.epam.esm.restapibasics.model.entity.GiftCertificate;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface GiftCertificateDao {

    /**
     * Retrieve certificate by its unique id.
     *
     * @param id certificate id
     * @return {@link GiftCertificate} wrapped by {@link Optional}
     */
    Optional<GiftCertificate> getById(Long id);

    /**
     * Retrieve certificates according to specified parameters. All parameters are optional, so
     * if they are not present, all certificates will be retrieved
     *
     * @param parameters map of all parameters
     * @return list of {@link GiftCertificate}
     */
    List<GiftCertificate> find(Paginator paginator, Map<String, SearchParameter> parameters);


    /**
     * Delete an existing certificate from the storage.
     *
     * @param giftCertificate entity to delete
     */
    void delete(GiftCertificate giftCertificate);

    /**
     * Update an existing certificate in the storage.
     *
     * @param giftCertificate {@link GiftCertificate} instance
     * @return updated {@link GiftCertificate}
     */
    GiftCertificate update(GiftCertificate giftCertificate);

    /**
     * Create a new certificate in the storage.
     *
     * @param giftCertificate {@link GiftCertificate} instance
     * @return created {@link GiftCertificate}
     */
    GiftCertificate create(GiftCertificate giftCertificate);
}
