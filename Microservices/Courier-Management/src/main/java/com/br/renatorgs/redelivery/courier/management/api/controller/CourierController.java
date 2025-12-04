package com.br.renatorgs.redelivery.courier.management.api.controller;

import com.br.renatorgs.redelivery.courier.management.api.model.CourierInput;
import com.br.renatorgs.redelivery.courier.management.api.model.CourierPayoutCalculationInput;
import com.br.renatorgs.redelivery.courier.management.api.model.CourierPayoutResultModel;
import com.br.renatorgs.redelivery.courier.management.domain.model.Courier;
import com.br.renatorgs.redelivery.courier.management.domain.repository.CourierRepository;
import com.br.renatorgs.redelivery.courier.management.domain.service.CourierPayoutService;
import com.br.renatorgs.redelivery.courier.management.domain.service.CourierRegistrationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.UUID;

@RequestMapping(value = "api/v1/couriers")
@RestController
@RequiredArgsConstructor
public class CourierController {

    @Autowired
    private CourierRepository courierRepository;
    @Autowired
    private CourierRegistrationService courierRegistrationService;
    @Autowired
    private CourierPayoutService courierPayoutService;

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public Courier create(@Valid @RequestBody CourierInput input){
        return courierRegistrationService.create(input);
    }

    @PutMapping("/{courierId}")
    @ResponseStatus(value = HttpStatus.CREATED)
    public Courier update(@Valid @RequestBody CourierInput input, @PathVariable UUID courierId){
        return courierRegistrationService.update(input, courierId);
    }

    @GetMapping
    public PagedModel<Courier> findAll(@PageableDefault Pageable pageable){
        return new PagedModel<>(courierRepository.findAll(pageable));
    }

    @GetMapping("/{courierId}")
    public Courier findById(@PathVariable UUID courierId){
        return courierRepository.findById(courierId).orElseThrow();
    }

    @PostMapping("/payout-calculation")
    public CourierPayoutResultModel calculate(@RequestBody CourierPayoutCalculationInput input){
        BigDecimal payoutFee = courierPayoutService.calculate(input.getDistanceInKm());

        return new CourierPayoutResultModel(payoutFee);
    }
}
