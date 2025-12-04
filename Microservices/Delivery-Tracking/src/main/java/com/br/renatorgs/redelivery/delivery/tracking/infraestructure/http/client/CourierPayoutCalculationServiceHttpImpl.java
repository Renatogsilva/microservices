package com.br.renatorgs.redelivery.delivery.tracking.infraestructure.http.client;

import com.br.renatorgs.redelivery.delivery.tracking.domain.service.CourierPayoutCalculationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@RequiredArgsConstructor
@Service
public class CourierPayoutCalculationServiceHttpImpl implements CourierPayoutCalculationService {

    private final CourierAPIClient courierAPIClient;

    @Override
    public BigDecimal calculatePayout(Double distanceInKm) {
        CourierPayoutResultModel courierPayoutResultModel = courierAPIClient
                .payoutCalculation(new CourierPayoutCalculationInput(distanceInKm));

        return courierPayoutResultModel.getPayoutFee();
    }
}
