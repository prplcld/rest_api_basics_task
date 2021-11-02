package com.epam.esm.restapibasics.api.controller;

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
     * @return JSON {@link ResponseEntity}
     */
    @PostMapping
    public ResponseEntity<Long> add(@RequestBody TagDto tagDto) {
        Long tagId = tagService.create(tagDto);
        return new ResponseEntity<>(tagId, HttpStatus.OK);
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
     * @return JSON {@link ResponseEntity} object that contains {@link TagDto} object
     */
    @GetMapping("/{id}")
    public ResponseEntity<TagDto> get(@PathVariable Long id) {
        TagDto tagDto = tagService.getById(id);
        return new ResponseEntity<>(tagDto, HttpStatus.OK);
    }

    /**
     * Retrieve all tags.
     *
     * @return JSON {@link ResponseEntity} object that contains list of {@link TagDto}
     */
    @GetMapping()
    public ResponseEntity<List<TagDto>> getAll() {
        List<TagDto> tagDtos = tagService.getAll(new Paginator(1, 10));
        return new ResponseEntity<>(tagDtos, HttpStatus.OK);
    }
}
