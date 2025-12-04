package com.br.renatorgs.redelivery.delivery.tracking.api.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class CourierIdInput {

    private UUID id;
}
