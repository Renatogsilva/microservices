package com.br.renatorgs.redelivery.delivery.tracking.domain.service;

import com.br.renatorgs.redelivery.delivery.tracking.domain.exception.DomainException;
import com.br.renatorgs.redelivery.delivery.tracking.domain.model.Delivery;
import com.br.renatorgs.redelivery.delivery.tracking.domain.repository.DeliveryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class DeliveryChekpointService {

    @Autowired
    private final DeliveryRepository deliveryRepository;

    public void place(UUID deliveryId) throws DomainException {
        Delivery delivery = deliveryRepository.findById(deliveryId).orElseThrow();

        if(delivery == null){
            throw new DomainException();
        }

        delivery.place();
        deliveryRepository.saveAndFlush(delivery);
    }

    public void pickup(UUID deliveryId, UUID courierId) throws DomainException {
        Delivery delivery = deliveryRepository.findById(deliveryId).orElseThrow();

        if(delivery == null){
            throw new DomainException();
        }

        delivery.pickUp(courierId);
        deliveryRepository.saveAndFlush(delivery);
    }

    public void complete(UUID deliveryId) throws DomainException {
        Delivery delivery = deliveryRepository.findById(deliveryId).orElseThrow();

        if(delivery == null){
            throw new DomainException();
        }

        delivery.markAsDelivery();
        deliveryRepository.saveAndFlush(delivery);
    }
}
