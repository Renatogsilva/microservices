package com.br.renatorgs.redelivery.courier.management.api.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class CourierPayoutCalculationInput {

    private Double distanceInKm;
}
