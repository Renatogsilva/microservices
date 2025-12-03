package com.br.renatorgs.redelivery.delivery.tracking.domain.repository;

import com.br.renatorgs.redelivery.delivery.tracking.domain.exception.DomainException;
import com.br.renatorgs.redelivery.delivery.tracking.domain.model.ContactPoint;
import com.br.renatorgs.redelivery.delivery.tracking.domain.model.Delivery;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;

import java.math.BigDecimal;
import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class DeliveryRepositoryTest {

    @Autowired
    private DeliveryRepository deliveryRepository;

    @Test
    public void shouldPersist() throws DomainException {
        Delivery delivery = Delivery.draft();

        delivery.editPreparationDetails(createdValidPreparationDetails());

        delivery.addItem("Computador", 2);
        delivery.addItem("Notebook", 2);

        deliveryRepository.saveAndFlush(delivery);
        Delivery deliveryPersist = deliveryRepository.findById(delivery.getId()).orElseThrow();

        assertEquals(2, deliveryPersist.getItems().size());
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