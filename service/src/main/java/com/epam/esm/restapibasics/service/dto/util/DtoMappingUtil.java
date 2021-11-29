package com.epam.esm.restapibasics.service.dto.util;

import com.epam.esm.restapibasics.model.entity.GiftCertificate;
import com.epam.esm.restapibasics.model.entity.Order;
import com.epam.esm.restapibasics.model.entity.Tag;
import com.epam.esm.restapibasics.model.entity.User;
import com.epam.esm.restapibasics.service.dto.GiftCertificateDto;
import com.epam.esm.restapibasics.service.dto.OrderDto;
import com.epam.esm.restapibasics.service.dto.TagDto;
import com.epam.esm.restapibasics.service.dto.UserDto;

import java.util.List;
import java.util.stream.Collectors;

public class DtoMappingUtil {

    public static UserDto mapToUserDto(User user) {
        UserDto userDto = new UserDto();

        userDto.setId(user.getId());
        userDto.setName(user.getName());

        return userDto;
    }

    public static Tag mapToTag(TagDto tagDto) {
        Tag tag = new Tag();

        tag.setId(tagDto.getId());
        tag.setName(tagDto.getName());

        return tag;
    }

    public static TagDto mapToTagDto(Tag tag) {
        TagDto tagDto = new TagDto();

        tagDto.setId(tag.getId());
        tagDto.setName(tag.getName());

        return tagDto;
    }

    public static GiftCertificate mapToCertificate(GiftCertificateDto giftCertificateDto) {
        GiftCertificate certificate = new GiftCertificate();

        certificate.setId(giftCertificateDto.getId());
        certificate.setName(giftCertificateDto.getName());
        certificate.setDescription(giftCertificateDto.getDescription());
        certificate.setPrice(giftCertificateDto.getPrice());
        certificate.setDuration(giftCertificateDto.getDuration());

        certificate.setTags(giftCertificateDto.getTags().stream()
        .map(DtoMappingUtil::mapToTag)
        .collect(Collectors.toList()));

        return certificate;
    }

    public static GiftCertificateDto mapToCertificateDto(GiftCertificate certificate) {
        GiftCertificateDto certificateDto = new GiftCertificateDto();

        certificateDto.setId(certificate.getId());
        certificateDto.setName(certificate.getName());
        certificateDto.setDescription(certificate.getDescription());
        certificateDto.setPrice(certificate.getPrice());
        certificateDto.setDuration(certificate.getDuration());
        certificateDto.setCreateDate(certificate.getCreateDate());
        certificateDto.setLastUpdateDate(certificate.getLastUpdateDate());

        certificateDto.setTags(certificate.getTags().stream()
                .map(DtoMappingUtil::mapToTagDto)
                .collect(Collectors.toList()));

        return certificateDto;
    }

    public static OrderDto mapToOrderDto(Order order) {
        OrderDto orderDto = new OrderDto();

        orderDto.setId(order.getId());
        orderDto.setUserId(order.getUser().getId());
        orderDto.setCost(order.getCost());
        orderDto.setPurchaseDate(order.getPurchaseDate());

        List<Long> certificates = order.getCertificates()
                .stream()
                .map(GiftCertificate::getId)
                .collect(Collectors.toList());

        orderDto.setCertificateIds(certificates);

        return orderDto;
    }
}
