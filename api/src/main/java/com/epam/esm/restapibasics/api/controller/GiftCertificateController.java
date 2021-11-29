package com.epam.esm.restapibasics.api.controller;

import com.epam.esm.restapibasics.api.hateoas.GiftCertificateHateoasAssembler;
import com.epam.esm.restapibasics.api.hateoas.GiftCertificateListHateoasAssembler;
import com.epam.esm.restapibasics.api.hateoas.model.GiftCertificateHateoasEntity;
import com.epam.esm.restapibasics.api.hateoas.model.GiftCertificateListHateoasEntity;
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
     * @return JSON {@link ResponseEntity} object that contains list of {@link GiftCertificateListHateoasEntity} objects
     */
    @GetMapping()
    public ResponseEntity<GiftCertificateListHateoasEntity> find(@RequestParam(required = false) String name,
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

        GiftCertificateListHateoasAssembler giftCertificateHateoasAssembler = new GiftCertificateListHateoasAssembler();
        GiftCertificateListHateoasEntity giftCertificateListHateoasEntity = giftCertificateHateoasAssembler.toModel(certificates);
        return new ResponseEntity<>(giftCertificateListHateoasEntity, HttpStatus.OK);
    }

    /**
     * Retrieve certificate by its unique id.
     *
     * @param id certificate id
     * @return JSON {@link ResponseEntity} object that contains {@link GiftCertificateHateoasEntity} object
     */
    @GetMapping("/{id}")
    public ResponseEntity<GiftCertificateHateoasEntity> getById(@PathVariable Long id) {
        GiftCertificateDto certificate = giftCertificateService.getById(id);
        GiftCertificateHateoasAssembler giftCertificateHateoasAssembler = new GiftCertificateHateoasAssembler();
        GiftCertificateHateoasEntity giftCertificateHateoasEntity = giftCertificateHateoasAssembler.toModel(certificate);
        return new ResponseEntity<>(giftCertificateHateoasEntity, HttpStatus.OK);
    }

    /**
     * Create a new certificate.
     *
     * @param giftCertificate {@link GiftCertificateDto} instance
     * @return JSON {@link ResponseEntity} object that contains created {@link GiftCertificateHateoasEntity} object
     */
    @PostMapping
    public ResponseEntity<GiftCertificateHateoasEntity> add(@RequestBody GiftCertificateDto giftCertificate) {
        GiftCertificateDto certificate = giftCertificateService.create(giftCertificate);
        GiftCertificateHateoasAssembler giftCertificateHateoasAssembler = new GiftCertificateHateoasAssembler();
        GiftCertificateHateoasEntity giftCertificateHateoasEntity = giftCertificateHateoasAssembler.toModel(certificate);
        return new ResponseEntity<>(giftCertificateHateoasEntity, HttpStatus.OK);
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
     * @return JSON {@link GiftCertificateHateoasEntity}
     */
    @PutMapping("/{id}")
    public ResponseEntity<GiftCertificateHateoasEntity> update(@RequestBody GiftCertificateDto giftCertificate, @PathVariable Long id) {
        giftCertificate.setId(id);
        GiftCertificateDto certificate = giftCertificateService.update(giftCertificate);
        GiftCertificateHateoasAssembler giftCertificateHateoasAssembler = new GiftCertificateHateoasAssembler();
        GiftCertificateHateoasEntity giftCertificateHateoasEntity = giftCertificateHateoasAssembler.toModel(certificate);
        return new ResponseEntity<>(giftCertificateHateoasEntity, HttpStatus.OK);
    }

}
