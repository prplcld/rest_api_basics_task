package com.epam.esm.restapibasics.api.controller;

import com.epam.esm.restapibasics.service.GiftCertificateService;
import com.epam.esm.restapibasics.service.dto.GiftCertificateDto;
import com.epam.esm.restapibasics.service.dto.SearchParamsModelDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
     * @param searchParamsModelDto {@link SearchParamsModelDto} instance
     * @return JSON {@link ResponseEntity} object that contains list of {@link GiftCertificateDto}
     */
    @GetMapping()
    public ResponseEntity<List<GiftCertificateDto>> find(@ModelAttribute SearchParamsModelDto searchParamsModelDto) {

        List<GiftCertificateDto> certificates = giftCertificateService.getAll(searchParamsModelDto);
        return new ResponseEntity<>(certificates, HttpStatus.OK);
    }

    /**
     * Retrieve certificate by its unique id.
     *
     * @param id certificate id
     * @return JSON {@link ResponseEntity} object that contains {@link GiftCertificateDto} object
     */
    @GetMapping("/{id}")
    public ResponseEntity<GiftCertificateDto> getById(@PathVariable Long id) {
        GiftCertificateDto certificate = giftCertificateService.getById(id);
        return new ResponseEntity<>(certificate, HttpStatus.OK);
    }

    /**
     * Create a new certificate.
     *
     * @param giftCertificate {@link GiftCertificateDto} instance
     * @return JSON {@link ResponseEntity} object that contains created {@link GiftCertificateDto} object
     */
    @PostMapping
    public ResponseEntity<Long> add(@RequestBody GiftCertificateDto giftCertificate) {
        return new ResponseEntity<>(giftCertificateService.create(giftCertificate), HttpStatus.OK);
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
    public ResponseEntity<Void> update(@RequestBody GiftCertificateDto giftCertificate, @PathVariable Long id) {
        giftCertificate.setId(id);
        giftCertificateService.update(giftCertificate);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
