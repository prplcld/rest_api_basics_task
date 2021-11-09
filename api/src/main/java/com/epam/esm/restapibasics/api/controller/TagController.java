package com.epam.esm.restapibasics.api.controller;

import com.epam.esm.restapibasics.api.hateoas.HateoasEntity;
import com.epam.esm.restapibasics.api.hateoas.HateoasListEntity;
import com.epam.esm.restapibasics.model.dao.Paginator;
import com.epam.esm.restapibasics.service.TagService;
import com.epam.esm.restapibasics.service.dto.TagDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
     * @return JSON {@link HateoasEntity}
     */
    @PostMapping
    public ResponseEntity<HateoasEntity<TagDto>> add(@RequestBody TagDto tagDto) {
        TagDto tag = tagService.create(tagDto);
        HateoasEntity<TagDto> hateoasEntity = HateoasEntity.build(tag);
        return new ResponseEntity<>(hateoasEntity, HttpStatus.OK);
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
     * @return JSON {@link HateoasEntity} object that contains {@link TagDto} object
     */
    @GetMapping("/{id}")
    public ResponseEntity<HateoasEntity<TagDto>> get(@PathVariable Long id) {
        TagDto tagDto = tagService.getById(id);
        HateoasEntity<TagDto> hateoasEntity = HateoasEntity.build(tagDto);
        return new ResponseEntity<>(hateoasEntity, HttpStatus.OK);
    }

    /**
     * Retrieve all tags.
     *
     * @return JSON {@link HateoasListEntity} object that contains list of {@link TagDto}
     */
    @GetMapping()
    public ResponseEntity<HateoasListEntity<TagDto>> getAll(@RequestParam(required = false) Integer page,
                                                            @RequestParam(required = false) Integer amount) {

        List<TagDto> tagDtos = tagService.getAll(new Paginator(page, amount));
        HateoasListEntity<TagDto> hateoasListEntity = HateoasListEntity.build(tagDtos, TagController.class);
        return new ResponseEntity<>(hateoasListEntity, HttpStatus.OK);
    }

    /**
     * Retrieve the most widely used tag of a user with the highest cost of all orders.
     *
     * @return JSON {@link ResponseEntity} object that contains {@link HateoasEntity} object
     */
    @GetMapping("/most_used_tag")
    public ResponseEntity<HateoasEntity<TagDto>> getMostWidelyTag() {
        TagDto tagDto = tagService.findMostWidelyUsedTag();
        HateoasEntity<TagDto> hateoasEntity = HateoasEntity.build(tagDto);
        return new ResponseEntity<>(hateoasEntity, HttpStatus.OK);
    }
}
