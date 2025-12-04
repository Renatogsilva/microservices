package com.br.renatorgs.redelivery.courier.management.domain.service;

import com.br.renatorgs.redelivery.courier.management.api.model.CourierInput;
import com.br.renatorgs.redelivery.courier.management.domain.model.Courier;
import com.br.renatorgs.redelivery.courier.management.domain.repository.CourierRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class CourierRegistrationService {

    @Autowired
    private CourierRepository courierRepository;

    public Courier create(CourierInput input) {
        Courier courier = Courier.brandNew(input.getName(), input.getPhone());

        return courierRepository.saveAndFlush(courier);
    }

    public Courier update(CourierInput input, UUID courierId) {
        Courier courier = courierRepository.findById(courierId).orElseThrow();

        courier.setPhone(input.getPhone());
        courier.setName(input.getName());

        return courierRepository.saveAndFlush(courier);
    }
}
