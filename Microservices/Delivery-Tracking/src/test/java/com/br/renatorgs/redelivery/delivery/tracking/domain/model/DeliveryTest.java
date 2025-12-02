package com.br.renatorgs.redelivery.delivery.tracking.domain.model;

import com.br.renatorgs.redelivery.delivery.tracking.domain.enumerators.EnumDeliveryStatus;
import com.br.renatorgs.redelivery.delivery.tracking.domain.exception.DomainException;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

class DeliveryTest {

    @Test
    public void shouldChangeToPlaced() throws DomainException {
        Delivery delivery = Delivery.draft();

        delivery.editPreparationDetails(createdValidPreparationDetails());
        delivery.place();

        assertEquals(EnumDeliveryStatus.WAITING_FOR_COURIER, delivery.getStatus());
        assertNotNull(delivery.getPlacedAt());
    }

    @Test
    public void shouldNotPlaced() throws DomainException {
        Delivery delivery = Delivery.draft();

        assertThrows(DomainException.class, () ->{
            delivery.place();
        });

        assertEquals(EnumDeliveryStatus.DRAFT, delivery.getStatus());
        assertNull(delivery.getPlacedAt());
    }

    private Delivery.PreparationDetails createdValidPreparationDetails(){
        ContactPoint sender = ContactPoint.builder()
                .zipCode("76328-804")
                .street("Rua SP, 155")
                .name("Abacaxi")
                .number("147A")
                .complement("Sala 514")
                .phone("(99)5565845-58")
                .build();

        ContactPoint recipient = ContactPoint.builder()
                .zipCode("71078-700")
                .street("Rua da silva, 04")
                .name("Morango")
                .number("77")
                .complement("Do lado da rua 01")
                .phone("(99)+5598710-9870")
                .build();

        return Delivery.PreparationDetails.builder()
                .sender(sender)
                .recipient(recipient)
                .distanceFee(new BigDecimal("15.00"))
                .courierPayout(new BigDecimal("5.85"))
                .expectedDeliveryTime(Duration.ofHours(5))
                .build();
    }
}