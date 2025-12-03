package com.br.renatorgs.redelivery.courier.management.domain.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.*;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Getter
@Setter(AccessLevel.PRIVATE)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity(name = "courier")
public class Courier {

    @Id
    @EqualsAndHashCode.Include
    private UUID id;

    @Setter(AccessLevel.PUBLIC)
    private String name;

    @Setter(AccessLevel.PUBLIC)
    private String phone;

    private Integer fulFilledDeliveriesQuantity;
    private Integer pendingDeliveriesQuatity;
    private OffsetDateTime lasFulFilledDeliveryAt;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "courier")
    private List<AssignedDelivery> pendingDeliveries = new ArrayList<>();

    private List<AssignedDelivery> getPendingDeliveries() {
        return Collections.unmodifiableList(this.pendingDeliveries);
    }

    public static Courier brandNew(String name, String phone) {
        Courier courier = new Courier();
        courier.setId(UUID.randomUUID());
        courier.setName(name);
        courier.setPhone(phone);
        courier.setPendingDeliveriesQuatity(0);
        courier.setFulFilledDeliveriesQuantity(0);

        return courier;
    }

    public void assign(UUID deliveryId) {
        this.pendingDeliveries.add(
                AssignedDelivery.pending(deliveryId, this)
        );

        this.pendingDeliveriesQuatity++;
    }

    public void fulFill(UUID deliveryId) {
        AssignedDelivery delivery = this.pendingDeliveries.stream().filter(d ->
                d.getId().equals(deliveryId)).findFirst().orElseThrow();

        this.pendingDeliveries.remove(delivery);
        this.pendingDeliveriesQuatity--;
        this.fulFilledDeliveriesQuantity++;
        this.lasFulFilledDeliveryAt = OffsetDateTime.now();
    }
}
