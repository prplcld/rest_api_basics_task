package com.epam.esm.restapibasics.api.controller;

import com.epam.esm.restapibasics.api.hateoas.TagHateoasAssembler;
import com.epam.esm.restapibasics.api.hateoas.TagListHateoasAssembler;
import com.epam.esm.restapibasics.api.hateoas.model.TagHateoasEntity;
import com.epam.esm.restapibasics.api.hateoas.model.TagListHateoasEntity;
import com.epam.esm.restapibasics.model.dao.Paginator;
import com.epam.esm.restapibasics.service.TagService;
import com.epam.esm.restapibasics.service.dto.TagDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/tags")
public class TagController {

    private final TagService tagService;

    @Autowired
    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    /**
     * Create a new tag.
     *
     * @param tagDto {@link TagDto} instance
     * @return JSON {@link TagHateoasEntity}
     */
    @PostMapping
    public ResponseEntity<TagHateoasEntity> add(@RequestBody TagDto tagDto) {
        TagDto tag = tagService.create(tagDto);
        TagHateoasAssembler tagHateoasAssembler = new TagHateoasAssembler();
        TagHateoasEntity tagHateoasEntity = tagHateoasAssembler.toModel(tag);
        return new ResponseEntity<>(tagHateoasEntity, HttpStatus.OK);
    }

    /**
     * Delete an existing tag.
     *
     * @param id tag id
     * @return empty {@link ResponseEntity}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        tagService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * Retrieve tag by its unique id.
     *
     * @param id tag id
     * @return JSON {@link TagHateoasEntity} object that contains {@link TagDto} object
     */
    @GetMapping("/{id}")
    public ResponseEntity<TagHateoasEntity> get(@PathVariable Long id) {
        TagDto tagDto = tagService.getById(id);
        TagHateoasAssembler tagHateoasAssembler = new TagHateoasAssembler();
        TagHateoasEntity tagHateoasEntity = tagHateoasAssembler.toModel(tagDto);
        return new ResponseEntity<>(tagHateoasEntity, HttpStatus.OK);
    }

    /**
     * Retrieve all tags.
     *
     * @return JSON {@link TagListHateoasEntity} object that contains list of {@link TagDto}
     */
    @GetMapping()
    public ResponseEntity<TagListHateoasEntity> getAll(@RequestParam(required = false) Integer page,
                                                       @RequestParam(required = false) Integer amount, Principal principal) {
        List<TagDto> tagDtos = tagService.getAll(new Paginator(page, amount));

        TagListHateoasAssembler tagListHateoasAssembler = new TagListHateoasAssembler();
        TagListHateoasEntity tagListHateoasEntity = tagListHateoasAssembler.toModel(tagDtos);

        return new ResponseEntity<>(tagListHateoasEntity, HttpStatus.OK);
    }

    /**
     * Retrieve the most widely used tag of a user with the highest cost of all orders.
     *
     * @return JSON {@link ResponseEntity} object that contains {@link TagHateoasEntity} object
     */
    @GetMapping("/most_used_tag")
    public ResponseEntity<TagHateoasEntity> getMostWidelyTag() {
        TagDto tagDto = tagService.findMostWidelyUsedTag();
        TagHateoasAssembler tagHateoasAssembler = new TagHateoasAssembler();
        TagHateoasEntity tagHateoasEntity = tagHateoasAssembler.toModel(tagDto);
        return new ResponseEntity<>(tagHateoasEntity, HttpStatus.OK);
    }
}
