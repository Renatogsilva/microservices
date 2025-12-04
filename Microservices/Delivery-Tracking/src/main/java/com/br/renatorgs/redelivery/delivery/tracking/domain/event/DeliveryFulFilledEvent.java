package com.br.renatorgs.redelivery.delivery.tracking.domain.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.time.OffsetDateTime;
import java.util.UUID;

@AllArgsConstructor
@Getter
@ToString
public class DeliveryFulFilledEvent {

    private final OffsetDateTime occuredAt;
    private final UUID deliveryId;
}
