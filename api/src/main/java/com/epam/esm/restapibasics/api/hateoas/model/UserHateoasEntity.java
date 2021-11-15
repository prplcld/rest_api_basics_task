package com.epam.esm.restapibasics.api.hateoas.model;

import com.epam.esm.restapibasics.service.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class UserHateoasEntity extends RepresentationModel<UserHateoasEntity> {
    private UserDto user;
}
