package com.br.renatorgs.redelivery.delivery.tracking.domain.enumerators;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EnumDeliveryStatusTest {


    @Test
    public void draft_canChangeToWaitingForCourier(){
        assertTrue(
                EnumDeliveryStatus.DRAFT.canChangeTo(EnumDeliveryStatus.WAITING_FOR_COURIER)
        );
    }

    @Test
    public void draft_canChangeToInTransit(){
        assertTrue(
                EnumDeliveryStatus.DRAFT.canNotChangeTo(EnumDeliveryStatus.IN_TRANSIT)
        );
    }
}