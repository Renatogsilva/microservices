package com.br.renatorgs.redelivery.delivery.tracking.domain.model;

import jakarta.persistence.Entity;
import lombok.*;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Getter
@Builder
@Entity(name = "item")
public class ContactPoint implements Serializable {

    private String zipCode;
    private String street;
    private String number;
    private String complement;
    private String name;
    private String phone;
}
