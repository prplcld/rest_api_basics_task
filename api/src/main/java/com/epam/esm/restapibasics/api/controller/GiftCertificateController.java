package com.epam.esm.restapibasics.api.controller;

import com.epam.esm.restapibasics.api.hateoas.HateoasEntity;
import com.epam.esm.restapibasics.api.hateoas.HateoasListEntity;
import com.epam.esm.restapibasics.model.dao.OrderType;
import com.epam.esm.restapibasics.model.dao.Paginator;
import com.epam.esm.restapibasics.model.dao.SearchParameter;
import com.epam.esm.restapibasics.model.dao.SearchParameterType;
import com.epam.esm.restapibasics.service.GiftCertificateService;
import com.epam.esm.restapibasics.service.dto.GiftCertificateDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
     * @return JSON {@link ResponseEntity} object that contains list of {@link HateoasListEntity} objects
     */
    @GetMapping()
    public ResponseEntity<HateoasListEntity<GiftCertificateDto>> find(@RequestParam(required = false) String name,
                                                  @RequestParam(required = false) String description,
                                                  @RequestParam(required = false) OrderType sortByDate,
                                                  @RequestParam(required = false) OrderType sortByName,
                                                  @RequestParam(required = false) List<String> tags,
                                                  @RequestParam(required = false) Integer page,
                                                  @RequestParam(required = false) Integer amount) {

        Map<String, SearchParameter> params = new HashMap<>();
        if (tags != null) {
            SearchParameter tagSearch = new SearchParameter(SearchParameterType.TAGS, tags);
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

        if(sortByDate != null) {
            List<String> values = new ArrayList<>();
            values.add(sortByDate.name());
            SearchParameter sortParameter = new SearchParameter(SearchParameterType.ORDER_BY, values);
            params.put("createDate", sortParameter);
        }

        if (sortByName != null) {
            List<String> values = new ArrayList<>();
            values.add(sortByName.name());
            SearchParameter sortParameter = new SearchParameter(SearchParameterType.ORDER_BY, values);
            params.put("name", sortParameter);
        }

        List<GiftCertificateDto> certificates = giftCertificateService.getAll(new Paginator(page, amount), params);
        HateoasListEntity<GiftCertificateDto> hateoasListEntity = HateoasListEntity.build(certificates, GiftCertificateController.class);
        return new ResponseEntity<>(hateoasListEntity, HttpStatus.OK);
    }

    /**
     * Retrieve certificate by its unique id.
     *
     * @param id certificate id
     * @return JSON {@link ResponseEntity} object that contains {@link HateoasEntity} object
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
     * @return JSON {@link ResponseEntity} object that contains created {@link HateoasEntity} object
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
     * @return JSON {@link HateoasEntity}
     */
    @PutMapping("/{id}")
    public ResponseEntity<HateoasEntity<GiftCertificateDto>> update(@RequestBody GiftCertificateDto giftCertificate, @PathVariable Long id) {
        giftCertificate.setId(id);
        GiftCertificateDto certificate = giftCertificateService.update(giftCertificate);
        HateoasEntity<GiftCertificateDto> hateoasEntity = HateoasEntity.build(certificate);
        return new ResponseEntity<>(hateoasEntity, HttpStatus.OK);
    }

}
