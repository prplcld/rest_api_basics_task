package com.epam.esm.rest_api_basics.service.impl;

import com.epam.esm.rest_api_basics.model.dao.GiftCertificateDao;
import com.epam.esm.rest_api_basics.model.dao.TagDao;
import com.epam.esm.rest_api_basics.model.dao.exception.NoDataFoundException;
import com.epam.esm.rest_api_basics.model.entity.GiftCertificate;
import com.epam.esm.rest_api_basics.model.entity.Tag;
import com.epam.esm.rest_api_basics.service.GiftCertificateService;
import com.epam.esm.rest_api_basics.service.exception.BadRequestException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
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
     * @param giftCertificate certificate object to be added.
     * @return certificate id value.
     */
    @Transactional(rollbackFor = Exception.class, timeout = 30)
    @Override
    public Long addGiftCertificate(GiftCertificate giftCertificate) {
        Long id = giftCertificateDao.addGiftCertificate(giftCertificate);
        addTagsToCertificate(giftCertificate.getTags(), id);
        return id;
    }

    /**
     * Gets all certificates with their tags.
     *
     * @return list of {@link GiftCertificate}
     */
    @Override
    public List<GiftCertificate> getAllCertificates() {
        List<GiftCertificate> giftCertificates = giftCertificateDao.getAllGiftCertificates();
        for (GiftCertificate g : giftCertificates) {
            g.setTags(tagDao.getTagsByCertificateId(g.getGiftCertificateId()));
        }
        return giftCertificates;
    }

    /**
     * Gets certificate by id.
     *
     * @param id certificate id value.
     * @return {@link GiftCertificate}
     */
    @Override
    public GiftCertificate getCertificateById(Long id) {
        GiftCertificate giftCertificate = giftCertificateDao.getGiftCertificateById(id);
        giftCertificate.setTags(tagDao.getTagsByCertificateId(giftCertificate.getGiftCertificateId()));
        return giftCertificate;
    }

    /**
     * Get certificates by tag name.
     *
     * @param name tag name.
     * @return list of {@link GiftCertificate}
     */
    @Override
    public List<GiftCertificate> getCertificatesByTagName(String name) {
        List<GiftCertificate> certificates = giftCertificateDao.getAllGiftCertificates();
        List<GiftCertificate> filtered = certificates.stream()
                .filter(c -> c.getTags()
                        .stream()
                        .anyMatch(t -> t.getName().equals(name)))
                .collect(Collectors.toList());

        return filtered;

    }

    /**
     * Get certificates by part of name/description.
     *
     * @param text search value.
     * @return list of {@link GiftCertificate}
     */
    @Override
    public List<GiftCertificate> getCertificatesByPartOfNameOrDescription(String text) {
        List<GiftCertificate> certificates = getAllCertificates();
        List<GiftCertificate> filtered = certificates.stream()
                .filter(c -> c.getName().contains(text) || c.getDescription().contains(text))
                .collect(Collectors.toList());

        return filtered;
    }


    /**
     * Delete an existing certificate.
     *
     * @param id certificate id
     * @throws BadRequestException
     */
    @Transactional(rollbackFor = Exception.class, timeout = 30)
    @Override
    public void deleteCertificate(Long id) {
        if (!giftCertificateDao.deleteCertificateFromTags(id)) {
            throw new BadRequestException();
        }
        if (!giftCertificateDao.deleteGiftCertificate(id)) {
            throw new BadRequestException();
        }
    }

    /**
     * Updates an existing certificate.
     *
     * @param giftCertificate certificate object
     * @throws BadRequestException in case when certificate with this id does not exist
     */
    @Transactional
    @Override
    public void updateGiftCertificate(GiftCertificate giftCertificate) {
        if (!giftCertificateDao.updateGiftCertificate(giftCertificate)) {
            throw new BadRequestException();
        }
        addTagsToCertificate(giftCertificate.getTags(), giftCertificate.getGiftCertificateId());
    }

    private void addTagsToCertificate(List<Tag> tags, Long certificateId) {
        for (Tag t : tags) {
            Long tagId;
            try {
                Tag tag = tagDao.getTagByName(t.getName());
                tagId = tag.getTagId();
            } catch (NoDataFoundException e) {
                tagId = tagDao.createTag(t);
            }

            if (!giftCertificateDao.addTagToCertificate(certificateId, tagId)) {
                throw new BadRequestException();
            }
        }
    }

    /**
     * Sorts certificates by date.
     *
     * @param reversed true for sort in desc order
     * @return list of {@link GiftCertificate}
     */
    @Override
    public List<GiftCertificate> sortGiftCertificatesByDate(boolean reversed) {
        List<GiftCertificate> giftCertificates = getAllCertificates();
        Comparator<GiftCertificate> comparator = Comparator.comparing(GiftCertificate::getCreateDate);
        if (reversed) {
            comparator = comparator.reversed();
        }
        return sortGiftCertificates(giftCertificates, comparator);
    }

    /**
     * Sorts certificates by name.
     *
     * @param reversed true for sort in desc order
     * @return list of {@link GiftCertificate}
     */
    @Override
    public List<GiftCertificate> sortGiftCertificateByName(boolean reversed) {
        List<GiftCertificate> giftCertificates = getAllCertificates();
        Comparator<GiftCertificate> comparator = Comparator.comparing(GiftCertificate::getName);
        if (reversed) {
            comparator = comparator.reversed();
        }
        return sortGiftCertificates(giftCertificates, comparator);
    }

    private List<GiftCertificate> sortGiftCertificates(List<GiftCertificate> giftCertificates, Comparator<GiftCertificate> comparator) {
        List<GiftCertificate> sorted = giftCertificates.stream()
                .sorted(comparator)
                .collect(Collectors.toList());
        return sorted;
    }
}
