package com.epam.esm.restapibasics.service.impl;

import com.epam.esm.restapibasics.model.dao.GiftCertificateDao;
import com.epam.esm.restapibasics.model.dao.Paginator;
import com.epam.esm.restapibasics.model.dao.SearchParameter;
import com.epam.esm.restapibasics.model.dao.TagDao;
import com.epam.esm.restapibasics.service.exception.EntityNotFoundException;
import com.epam.esm.restapibasics.model.entity.GiftCertificate;
import com.epam.esm.restapibasics.model.entity.Tag;
import com.epam.esm.restapibasics.service.GiftCertificateService;
import com.epam.esm.restapibasics.service.dto.GiftCertificateDto;
import com.epam.esm.restapibasics.service.dto.util.DtoMappingUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.time.ZoneOffset.UTC;

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
    public GiftCertificateDto create(GiftCertificateDto giftCertificateDto) {
        GiftCertificate giftCertificate = DtoMappingUtil.mapToCertificate(giftCertificateDto);

        LocalDateTime createDate = LocalDateTime.now(UTC);
        giftCertificate.setCreateDate(createDate);
        giftCertificate.setLastUpdateDate(createDate);

        List<Tag> tags = giftCertificate.getTags();
        if (tags != null) {
            giftCertificate.setTags(processTags(tags));
        }

        return DtoMappingUtil.mapToCertificateDto(giftCertificateDao.create(giftCertificate));
    }


    /**
     * Gets all certificates with their tags.
     *
     * @return list of {@link GiftCertificateDto}
     */
    @Override
    public List<GiftCertificateDto> getAll(Paginator paginator, Map<String, SearchParameter> parameters) {
        List<GiftCertificate> giftCertificates = giftCertificateDao.find(paginator, parameters);
        return giftCertificates.stream()
                .map(DtoMappingUtil::mapToCertificateDto)
                .collect(Collectors.toList());
    }

    /**
     * Gets certificate by id.
     *
     * @param id certificate id value.
     * @return {@link GiftCertificateDto}
     */
    @Override
    public GiftCertificateDto getById(Long id) {
        GiftCertificate certificate = giftCertificateDao.getById(id).orElseThrow(() ->
                new EntityNotFoundException(id, GiftCertificate.class));

        return DtoMappingUtil.mapToCertificateDto(certificate);
    }


    /**
     * Delete an existing certificate.
     *
     * @param id certificate id
     */
    @Transactional(rollbackFor = Exception.class, timeout = 30)
    @Override
    public void delete(Long id) {
        GiftCertificate certificate = giftCertificateDao.getById(id)
                .orElseThrow(() -> new EntityNotFoundException(id, GiftCertificate.class));
        giftCertificateDao.delete(certificate);
    }

    /**
     * Updates an existing certificate.
     *
     * @param giftCertificateDto certificate object
     */
    @Transactional(rollbackFor = Exception.class, timeout = 30)
    @Override
    public GiftCertificateDto update(GiftCertificateDto giftCertificateDto) {
        //FIXME add tag
        Long certificateId = giftCertificateDto.getId();
        GiftCertificate giftCertificate = giftCertificateDao.getById(certificateId)
                .orElseThrow(() -> new EntityNotFoundException(certificateId, GiftCertificate.class));

        if (giftCertificateDto.getName() != null) {
            giftCertificate.setName(giftCertificateDto.getName());
        }

        if (giftCertificateDto.getDescription() != null) {
            giftCertificate.setDescription(giftCertificateDto.getDescription());
        }

        if (giftCertificateDto.getDuration() != null) {
            giftCertificate.setDuration(giftCertificateDto.getDuration());
        }

        if (giftCertificateDto.getPrice() != null) {
            giftCertificate.setPrice(giftCertificateDto.getPrice());
        }

        LocalDateTime lastUpdateDate = LocalDateTime.now(UTC);
        giftCertificate.setLastUpdateDate(lastUpdateDate);

        List<Tag> tags = giftCertificate.getTags();
        if (tags != null) {
            giftCertificate.setTags(processTags(tags));
        }

        return DtoMappingUtil.mapToCertificateDto(giftCertificateDao.update(giftCertificate));
    }

    private List<Tag> processTags(List<Tag> tags) {
        return tags.stream()
                .map(t -> {
                    Optional<Tag> optionalTag = tagDao.getByName(t.getName());
                    Tag tag;

                    if (optionalTag.isEmpty()) {
                        tag = new Tag();
                        tag.setName(t.getName());
                        tag.setId(tagDao.create(tag).getId());
                    } else {
                        tag = optionalTag.get();
                    }

                    return tag;
                }).collect(Collectors.toCollection(ArrayList::new));
    }
}
