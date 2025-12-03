package com.br.renatorgs.redelivery.delivery.tracking.domain.service;

import com.br.renatorgs.redelivery.delivery.tracking.api.model.ContactPointInput;
import com.br.renatorgs.redelivery.delivery.tracking.api.model.DeliveryInput;
import com.br.renatorgs.redelivery.delivery.tracking.api.model.ItemInput;
import com.br.renatorgs.redelivery.delivery.tracking.domain.exception.DomainException;
import com.br.renatorgs.redelivery.delivery.tracking.domain.model.ContactPoint;
import com.br.renatorgs.redelivery.delivery.tracking.domain.model.Delivery;
import com.br.renatorgs.redelivery.delivery.tracking.domain.repository.DeliveryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DeliveryPreparationService {

    @Autowired
    private DeliveryRepository deliveryRepository;

    @Transactional
    public Delivery draft(DeliveryInput input) throws DomainException {
        Delivery delivery = Delivery.draft();

        handlePreparation(input, delivery);

        return deliveryRepository.saveAndFlush(delivery);
    }

    @Transactional
    public Delivery edit(UUID deliveryId, DeliveryInput input) throws DomainException {
        Delivery delivery = deliveryRepository.findById(deliveryId).orElseThrow();

        if(delivery  == null){
            throw new DomainException("");
        }

        delivery.removeItem();
        handlePreparation(input, delivery);

        return deliveryRepository.saveAndFlush(delivery);
    }

    private void handlePreparation(DeliveryInput input, Delivery delivery) throws DomainException {
        ContactPointInput senderInput = input.getSender();
        ContactPointInput recipientInput = input.getRecipient();

        ContactPoint sender = ContactPoint.builder()
                .name(senderInput.getName())
                .phone(senderInput.getPhone())
                .complement(senderInput.getComplement())
                .street(senderInput.getStreet())
                .zipCode(senderInput.getZipCode())
                .number(senderInput.getNumber())
                .build();

        ContactPoint recipient = ContactPoint.builder()
                .number(recipientInput.getNumber())
                .zipCode(recipientInput.getZipCode())
                .street(recipientInput.getStreet())
                .complement(recipientInput.getComplement())
                .phone(recipientInput.getPhone())
                .name(recipientInput.getName())
                .build();

        Duration expectedDeliveryTime = Duration.ofHours(3);
        BigDecimal payout = new BigDecimal("10");

        var preparationDetails = Delivery.PreparationDetails.builder()
                .recipient(recipient)
                .sender(sender)
                .expectedDeliveryTime(expectedDeliveryTime)
                .courierPayout(payout)
                .distanceFee(new BigDecimal("10"))
                .build();

        delivery.editPreparationDetails(preparationDetails);

        for (ItemInput itemInput : input.getItems()) {
            delivery.addItem(itemInput.getName(), itemInput.getQuantity());
        }
    }

}
