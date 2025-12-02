package com.br.renatorgs.redelivery.delivery.tracking.domain.enumerators;

import java.util.Arrays;
import java.util.List;

public enum EnumDeliveryStatus {
    DRAFT,
    WAITING_FOR_COURIER(DRAFT),
    IN_TRANSIT(WAITING_FOR_COURIER),
    DELIVERY(IN_TRANSIT);

    private final List<EnumDeliveryStatus> previousStatuses;

    EnumDeliveryStatus(EnumDeliveryStatus... previousStatuses){
        this.previousStatuses = Arrays.asList(previousStatuses);
    }

    public boolean canNotChangeTo(EnumDeliveryStatus status){
        EnumDeliveryStatus current = this;

        return !status.previousStatuses.contains(current);
    }

    public boolean canChangeTo(EnumDeliveryStatus status){
        return !canNotChangeTo(status);
    }
}