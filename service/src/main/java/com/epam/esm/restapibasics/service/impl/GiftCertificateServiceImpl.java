package com.epam.esm.restapibasics.service.impl;

import com.epam.esm.restapibasics.model.dao.GiftCertificateDao;
import com.epam.esm.restapibasics.model.dao.OrderType;
import com.epam.esm.restapibasics.model.dao.TagDao;
import com.epam.esm.restapibasics.model.dao.exception.EntityNotFoundException;
import com.epam.esm.restapibasics.model.entity.GiftCertificate;
import com.epam.esm.restapibasics.model.entity.Tag;
import com.epam.esm.restapibasics.service.GiftCertificateService;
import com.epam.esm.restapibasics.service.dto.GiftCertificateDto;
import com.epam.esm.restapibasics.service.dto.SearchParamsModelDto;
import com.epam.esm.restapibasics.service.dto.TagDto;
import com.epam.esm.restapibasics.service.exception.DaoResultException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GiftCertificateServiceImpl implements GiftCertificateService {

    private final GiftCertificateDao giftCertificateDao;
    private final TagDao tagDao;


    public GiftCertificateServiceImpl(GiftCertificateDao giftCertificateDao, TagDao tagDao) {
        this.giftCertificateDao = giftCertificateDao;
        this.tagDao = tagDao;
    }

    /**
     * Adds new gift certificate.
     *
     * @param giftCertificateDto certificate object to be added.
     * @return certificate id value.
     */
    @Transactional(rollbackFor = Exception.class, timeout = 30)
    @Override
    public Long create(GiftCertificateDto giftCertificateDto) {
        GiftCertificate giftCertificate = giftCertificateDto.toCertificate();
        Long id = giftCertificateDao.create(giftCertificate);

        addTagsToCertificate(giftCertificateDto.getTags(), id);
        return id;
    }

    private void addTagsToCertificate(List<TagDto> tags, Long certificateId) {
        for (TagDto t : tags) {
            Long tagId;
                Optional<Tag> tag = tagDao.getByName(t.getName());
                if (tag.isPresent()) {
                    tagId = tag.get().getId();
                }
                else {
                    tagId = tagDao.create(t.toTag());
                }
            if (!giftCertificateDao.attachTag(certificateId, tagId)) {
                throw new DaoResultException();
            }
        }
    }

    /**
     * Gets all certificates with their tags.
     *
     * @return list of {@link GiftCertificateDto}
     */
    @Override
    public List<GiftCertificateDto> getAll(SearchParamsModelDto searchParamsModelDto) {
        String tagName = searchParamsModelDto.getTagName();
        String certificateName = searchParamsModelDto.getCertificateName();
        String certificateDescription = searchParamsModelDto.getCertificateDescription();
        OrderType orderByName = searchParamsModelDto.getOrderByName();
        OrderType orderByCreateDate = searchParamsModelDto.getOrderByCreateDate();


        List<GiftCertificate> giftCertificates = giftCertificateDao.find(tagName, certificateName, certificateDescription, orderByName, orderByCreateDate);

        return giftCertificates.stream()
                .map(certificate -> {
                    Long certificateId = certificate.getId();
                    List<Tag> tags = tagDao.getByCertificateId(certificateId);
                    return GiftCertificateDto.createFromCertificate(certificate, tags);
                }).collect(Collectors.toList());
    }

    /**
     * Gets certificate by id.
     *
     * @param id certificate id value.
     * @return {@link GiftCertificateDto}
     */
    @Override
    public GiftCertificateDto getById(Long id) {
        GiftCertificate giftCertificate = giftCertificateDao.getById(id);
        List<Tag> tags = tagDao.getByCertificateId(giftCertificate.getId());

        return GiftCertificateDto.createFromCertificate(giftCertificate, tags);
    }


    /**
     * Delete an existing certificate.
     *
     * @param id certificate id
     */
    @Transactional(rollbackFor = Exception.class, timeout = 30)
    @Override
    public void delete(Long id) {
        giftCertificateDao.deleteFromTags(id);
        if (!giftCertificateDao.delete(id)) {
            throw new EntityNotFoundException(id);
        }
    }

    /**
     * Updates an existing certificate.
     *
     * @param giftCertificateDto certificate object
     */
    @Transactional
    @Override
    public void update(GiftCertificateDto giftCertificateDto) {
        GiftCertificate giftCertificate = giftCertificateDto.toCertificate();
        if (!giftCertificateDao.update(giftCertificate)) {
            throw new EntityNotFoundException(giftCertificate.getId());
        }
        List<TagDto> tags = giftCertificateDto.getTags();
        addTagsToCertificate(tags, giftCertificate.getId());
    }
}
