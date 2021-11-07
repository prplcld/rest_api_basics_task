package com.epam.esm.restapibasics.api.controller;

import com.epam.esm.restapibasics.api.hateoas.HateoasEntity;
import com.epam.esm.restapibasics.model.dao.OrderType;
import com.epam.esm.restapibasics.model.dao.Paginator;
import com.epam.esm.restapibasics.model.dao.SearchParameter;
import com.epam.esm.restapibasics.model.dao.SearchParameterType;
import com.epam.esm.restapibasics.service.GiftCertificateService;
import com.epam.esm.restapibasics.service.dto.GiftCertificateDto;
import com.epam.esm.restapibasics.service.dto.TagDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/certificates")
public class GiftCertificateController {

    private final GiftCertificateService giftCertificateService;

    @Autowired
    public GiftCertificateController(GiftCertificateService giftCertificateService) {
        this.giftCertificateService = giftCertificateService;
    }

    /**
     * Retrieve certificates according to specified parameters.
     * All parameters are optional, so if they are not present, all certificates will be retrieved.
     *
     * @return JSON {@link ResponseEntity} object that contains list of {@link GiftCertificateDto}
     */
    @GetMapping()
    public ResponseEntity<List<GiftCertificateDto>> find(@RequestParam(required = false) String name,
                                                         @RequestParam(required = false) String description,
                                                         @RequestParam(required = false) OrderType sortByDate,
                                                         @RequestParam(required = false) OrderType sortByName,
                                                         @RequestParam(required = false) List<TagDto> tags,
                                                         @RequestParam(required = false) Integer page,
                                                         @RequestParam(required = false) Integer amount) {

        Map<String, SearchParameter> params = new HashMap<>();
        if (tags != null) {
            List<String> tagNames = tags.stream().map(TagDto::getName).collect(Collectors.toList());
            SearchParameter tagSearch = new SearchParameter(SearchParameterType.TAGS, tagNames);
            params.put("tags", tagSearch);
        }

        if (name != null) {
            List<String> values = new ArrayList<>();
            values.add(name);
            SearchParameter nameSearch = new SearchParameter(SearchParameterType.SEARCH, values);
            params.put("name", nameSearch);
        }

        if (description != null) {
            List<String> values = new ArrayList<>();
            values.add(description);
            SearchParameter nameSearch = new SearchParameter(SearchParameterType.SEARCH, values);
            params.put("description", nameSearch);
        }

        List<GiftCertificateDto> certificates = giftCertificateService.getAll(new Paginator(page, amount), params);
        return new ResponseEntity<>(certificates, HttpStatus.OK);
    }

    /**
     * Retrieve certificate by its unique id.
     *
     * @param id certificate id
     * @return JSON {@link ResponseEntity} object that contains {@link GiftCertificateDto} object
     */
    @GetMapping("/{id}")
    public ResponseEntity<HateoasEntity<GiftCertificateDto>> getById(@PathVariable Long id) {
        GiftCertificateDto certificate = giftCertificateService.getById(id);
        HateoasEntity<GiftCertificateDto> hateoasEntity = HateoasEntity.build(certificate);
        return new ResponseEntity<>(hateoasEntity, HttpStatus.OK);
    }

    /**
     * Create a new certificate.
     *
     * @param giftCertificate {@link GiftCertificateDto} instance
     * @return JSON {@link ResponseEntity} object that contains created {@link GiftCertificateDto} object
     */
    @PostMapping
    public ResponseEntity<HateoasEntity<GiftCertificateDto>> add(@RequestBody GiftCertificateDto giftCertificate) {
        GiftCertificateDto certificate = giftCertificateService.create(giftCertificate);
        HateoasEntity<GiftCertificateDto> hateoasEntity = HateoasEntity.build(certificate);
        return new ResponseEntity<>(hateoasEntity, HttpStatus.OK);
    }

    /**
     * Delete an existing certificate.
     *
     * @param id certificate id
     * @return empty {@link ResponseEntity}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        giftCertificateService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * Update an existing certificate.
     *
     * @param id             certificate id
     * @return JSON {@link ResponseEntity}
     */
    @PutMapping("/{id}")
    public ResponseEntity<HateoasEntity<GiftCertificateDto>> update(@RequestBody GiftCertificateDto giftCertificate, @PathVariable Long id) {
        giftCertificate.setId(id);
        GiftCertificateDto certificate = giftCertificateService.update(giftCertificate);
        HateoasEntity<GiftCertificateDto> hateoasEntity = HateoasEntity.build(certificate);
        return new ResponseEntity<>(hateoasEntity, HttpStatus.OK);
    }

}
