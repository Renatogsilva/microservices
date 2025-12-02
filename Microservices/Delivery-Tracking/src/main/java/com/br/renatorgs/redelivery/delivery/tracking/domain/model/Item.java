package com.br.renatorgs.redelivery.delivery.tracking.domain.model;

import jakarta.persistence.Entity;
import lombok.*;

import java.io.Serializable;
import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PACKAGE)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Setter(AccessLevel.PRIVATE)
@Getter
@Entity(name = "item")
public class Item implements Serializable {

    @EqualsAndHashCode.Include
    private UUID id;
    private String name;
    private Integer quantity;

    static Item brandNew(String name, Integer quantity){
        Item item = new Item();
        item.setId(UUID.randomUUID());
        item.setName(name);
        item.setQuantity(quantity);

        return item;
    }
}
