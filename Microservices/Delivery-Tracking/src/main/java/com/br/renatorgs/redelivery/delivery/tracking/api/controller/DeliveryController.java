package com.br.renatorgs.redelivery.delivery.tracking.api.controller;

import com.br.renatorgs.redelivery.delivery.tracking.api.model.DeliveryInput;
import com.br.renatorgs.redelivery.delivery.tracking.domain.exception.DomainException;
import com.br.renatorgs.redelivery.delivery.tracking.domain.model.Delivery;
import com.br.renatorgs.redelivery.delivery.tracking.domain.repository.DeliveryRepository;
import com.br.renatorgs.redelivery.delivery.tracking.domain.service.DeliveryPreparationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@RestController
@RequestMapping(value = "/api/v1/deliveries")
@RequiredArgsConstructor
public class DeliveryController {

    @Autowired
    private DeliveryPreparationService deliveryPreparationService;
    @Autowired
    private DeliveryRepository deliveryRepository;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Delivery draft(@RequestBody @Valid DeliveryInput input) throws DomainException {
        return deliveryPreparationService.draft(input);
    }

    @PutMapping(value = "/{deliveryId}")
    @ResponseStatus(HttpStatus.CREATED)
    public Delivery edit(@PathVariable UUID deliveryId, @RequestBody @Valid DeliveryInput input) throws DomainException {
        return deliveryPreparationService.edit(deliveryId, input);
    }

    @GetMapping
    public PagedModel<Delivery> findAll(@PageableDefault Pageable pageable) {
        return new PagedModel<>(
                deliveryRepository.findAll(pageable));
    }

    @GetMapping("/{deliveryId}")
    public Delivery findById(@PathVariable UUID deliveryId) {
        return deliveryRepository.findById(deliveryId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
}
