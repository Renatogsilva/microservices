package com.br.renatorgs.redelivery.delivery.tracking.domain.model;

import jakarta.persistence.Embeddable;

import lombok.*;

import java.io.Serializable;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@EqualsAndHashCode
@Getter
@Builder
public class ContactPoint implements Serializable {

    private String zipCode;
    private String street;
    private String number;
    private String complement;
    private String name;
    private String phone;
}
