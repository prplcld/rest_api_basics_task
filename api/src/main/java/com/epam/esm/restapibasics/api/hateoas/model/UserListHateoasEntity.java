package com.epam.esm.restapibasics.api.hateoas.model;

import com.epam.esm.restapibasics.service.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class UserListHateoasEntity extends RepresentationModel<UserListHateoasEntity> {
    private List<UserDto> users;
}
