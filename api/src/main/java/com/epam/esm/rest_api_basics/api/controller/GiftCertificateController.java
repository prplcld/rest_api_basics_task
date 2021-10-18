package com.epam.esm.rest_api_basics.api.controller;

import com.epam.esm.rest_api_basics.model.entity.GiftCertificate;
import com.epam.esm.rest_api_basics.model.entity.Tag;
import com.epam.esm.rest_api_basics.service.GiftCertificateService;
import com.epam.esm.rest_api_basics.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/certificates")
public class GiftCertificateController {

    private final TagService tagService;
    private final GiftCertificateService giftCertificateService;

    @Autowired
    public GiftCertificateController(TagService tagService, GiftCertificateService giftCertificateService) {
        this.tagService = tagService;
        this.giftCertificateService = giftCertificateService;
    }

    @GetMapping()
    public List<GiftCertificate> getCertificatesByTagName(@RequestParam(value = "tagName", required = false) String tagName,
                                                          @RequestParam(value = "contains", required = false) String text) {
        if (text != null) {
            return giftCertificateService.getCertificatesByPartOfNameOrDescription(text);
        }
        if (tagName != null) {
            return giftCertificateService.getCertificatesByTagName(tagName);
        }
        return giftCertificateService.getAllCertificates();
    }

    @GetMapping("/sort")
    public List<GiftCertificate> sortGiftCertificates(@RequestParam(value = "sortBy") SortBy sortBy, @RequestParam(value = "desc") boolean desc) {
        return switch (sortBy) {
            case DATE -> giftCertificateService.sortGiftCertificatesByDate(desc);
            case NAME -> giftCertificateService.sortGiftCertificateByName(desc);
        };
    }

    @GetMapping("/{id:[\\d]+}")
    public GiftCertificate getCertificateById(@PathVariable Long id) {
        return giftCertificateService.getCertificateById(id);
    }

    @PostMapping
    public Long addCertificate(@RequestBody GiftCertificate giftCertificate) {
        return giftCertificateService.addGiftCertificate(giftCertificate);
    }

    @DeleteMapping("/{id:[\\d]+}")
    public void deleteCertificate(@PathVariable Long id) {
        giftCertificateService.deleteCertificate(id);
    }

    @PutMapping("{id:[\\d]+}")
    public void updateCertificate(@RequestBody GiftCertificate giftCertificate, @PathVariable Long id) {
        giftCertificate.setGiftCertificateId(id);
        giftCertificateService.updateGiftCertificate(giftCertificate);
    }

    @PostMapping("/tag")
    public void addTag(@RequestBody Tag tag) {
        tagService.addTag(tag);
    }

    @DeleteMapping("/tag/{id:[\\d]+}")
    public void deleteTag(@PathVariable Long id) {
        tagService.deleteTag(id);
    }

    @GetMapping("/tag/{id:[\\d]+}")
    public Tag getTag(@PathVariable Long id) {
        return tagService.getById(id);
    }

    @GetMapping("/tag")
    public List<Tag> getAllTags() {
        return tagService.getAllTags();
    }

    @InitBinder("sortBy")
    public void initSortTypeBinder(WebDataBinder dataBinder) {
        dataBinder.registerCustomEditor(SortBy.class, new SortByEditor());
    }
}
