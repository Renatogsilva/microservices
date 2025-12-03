package com.br.renatorgs.redelivery.delivery.tracking.api.model;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class ContactPointInput {

    @NotBlank
    private String zipCode;
    @NotBlank
    private String street;
    @NotBlank
    private String name;
    @NotBlank
    private String number;
    private String complement;
    @NotBlank
    private String phone;
}
