package com.br.renatorgs.redelivery.delivery.tracking.domain.service;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Duration;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class DeliveryEstimate {

    private Duration estimatedTime;
    private Double distanceInKm;
}
