package com.epam.esm.restapibasics.service.dto;

import com.epam.esm.restapibasics.model.entity.Tag;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TagDto {
    private Long id;
    private String name;

    public Tag toTag() {
        Tag tag = new Tag();

        tag.setId(id);
        tag.setName(name);

        return tag;
    }

    public static TagDto fromTag(Tag tag) {
        TagDto tagDto = new TagDto();

        tagDto.setId(tag.getId());
        tagDto.setName(tag.getName());

        return tagDto;
    }
}
