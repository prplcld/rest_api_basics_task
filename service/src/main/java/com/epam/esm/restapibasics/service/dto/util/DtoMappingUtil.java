package com.epam.esm.restapibasics.service.dto.util;

import com.epam.esm.restapibasics.model.entity.Tag;
import com.epam.esm.restapibasics.model.entity.User;
import com.epam.esm.restapibasics.service.dto.TagDto;
import com.epam.esm.restapibasics.service.dto.UserDto;

public class DtoMappingUtil {

    public static UserDto mapFromUser(User user) {
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

    public static TagDto mapFromTag(Tag tag) {
        TagDto tagDto = new TagDto();

        tagDto.setId(tag.getId());
        tagDto.setName(tag.getName());

        return tagDto;
    }
}
