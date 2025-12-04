package com.br.renatorgs.redelivery.courier.management.api.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class CourierPayoutResultModel {

    private BigDecimal payoutFee;
}
