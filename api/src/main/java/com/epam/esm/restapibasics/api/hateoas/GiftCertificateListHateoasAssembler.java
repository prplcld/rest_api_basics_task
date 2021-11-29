package com.epam.esm.restapibasics.api.hateoas;

import com.epam.esm.restapibasics.api.controller.GiftCertificateController;
import com.epam.esm.restapibasics.api.hateoas.model.GiftCertificateListHateoasEntity;
import com.epam.esm.restapibasics.service.dto.GiftCertificateDto;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;

import java.util.List;

public class GiftCertificateListHateoasAssembler extends RepresentationModelAssemblerSupport<List<GiftCertificateDto>, GiftCertificateListHateoasEntity> {

    public GiftCertificateListHateoasAssembler() {
        super(GiftCertificateController.class, GiftCertificateListHateoasEntity.class);
    }

    @Override
    public GiftCertificateListHateoasEntity toModel(List<GiftCertificateDto> entity) {
        GiftCertificateListHateoasEntity giftCertificateListHateoasEntity = new GiftCertificateListHateoasEntity(entity);
        giftCertificateListHateoasEntity.add(LinkBuilder.buildControllerLink(getControllerClass()));
        return giftCertificateListHateoasEntity;
    }
}
