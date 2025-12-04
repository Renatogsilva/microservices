package com.br.renatorgs.redelivery.delivery.tracking.domain.service;

import com.br.renatorgs.redelivery.delivery.tracking.domain.model.ContactPoint;

public interface DeliveryTimeEstimationService {

    DeliveryEstimate estimate(ContactPoint sender, ContactPoint receiver);
}
