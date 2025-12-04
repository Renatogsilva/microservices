package com.br.renatorgs.redelivery.courier.management.api.model;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class CourierInput {

    @NotBlank
    private String name;

    @NotBlank
    private String phone;
}
