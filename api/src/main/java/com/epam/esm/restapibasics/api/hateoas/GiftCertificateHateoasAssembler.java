package com.epam.esm.restapibasics.api.hateoas;

import com.epam.esm.restapibasics.api.controller.GiftCertificateController;
import com.epam.esm.restapibasics.api.hateoas.model.GiftCertificateHateoasEntity;
import com.epam.esm.restapibasics.service.dto.GiftCertificateDto;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;

public class GiftCertificateHateoasAssembler extends RepresentationModelAssemblerSupport<GiftCertificateDto, GiftCertificateHateoasEntity> {

    public GiftCertificateHateoasAssembler() {
        super(GiftCertificateController.class, GiftCertificateHateoasEntity.class);
    }

    @Override
    public GiftCertificateHateoasEntity toModel(GiftCertificateDto entity) {
        GiftCertificateHateoasEntity giftCertificateHateoasEntity = createModelWithId(entity.getId(), entity);
        giftCertificateHateoasEntity.add(LinkBuilder.buildControllerLink(getControllerClass()));
        giftCertificateHateoasEntity.setGiftCertificate(entity);
        return giftCertificateHateoasEntity;
    }
}
