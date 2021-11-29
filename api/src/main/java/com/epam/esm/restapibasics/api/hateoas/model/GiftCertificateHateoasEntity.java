package com.epam.esm.restapibasics.api.hateoas.model;

import com.epam.esm.restapibasics.service.dto.GiftCertificateDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class GiftCertificateHateoasEntity extends RepresentationModel<GiftCertificateHateoasEntity> {
    private GiftCertificateDto giftCertificate;
}
