package com.br.renatorgs.redelivery.delivery.tracking.domain.model;

import com.br.renatorgs.redelivery.delivery.tracking.domain.enumerators.EnumDeliveryStatus;
import com.br.renatorgs.redelivery.delivery.tracking.domain.event.DeliveryFulFilledEvent;
import com.br.renatorgs.redelivery.delivery.tracking.domain.event.DeliveryPickUpEvent;
import com.br.renatorgs.redelivery.delivery.tracking.domain.event.DeliveryPlacedEvent;
import com.br.renatorgs.redelivery.delivery.tracking.domain.exception.DomainException;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.domain.AbstractAggregateRoot;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PACKAGE)
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@Setter(AccessLevel.PRIVATE)
@Getter
@Entity(name = "delivery")
public class Delivery extends AbstractAggregateRoot<Delivery> implements Serializable {

    @Id
    @EqualsAndHashCode.Include
    private UUID id;
    private UUID courierId;
    private OffsetDateTime placedAt;
    private OffsetDateTime assignedAt;
    private OffsetDateTime expectedDeliveryAt;
    private OffsetDateTime fulFilledAt;
    private BigDecimal distanceFee;
    private BigDecimal courierPayout;
    private BigDecimal totalCost;
    private Integer totalItems;
    private EnumDeliveryStatus status;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "zipCode", column = @Column(name = "sender_zip_code")),
            @AttributeOverride(name = "street", column = @Column(name = "sender_street")),
            @AttributeOverride(name = "number", column = @Column(name = "sender_number")),
            @AttributeOverride(name = "complement", column = @Column(name = "sender_complement")),
            @AttributeOverride(name = "name", column = @Column(name = "sender_name")),
            @AttributeOverride(name = "phone", column = @Column(name = "sender_phone"))
    })
    private ContactPoint sender;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "zipCode", column = @Column(name = "recipient_zip_code")),
            @AttributeOverride(name = "street", column = @Column(name = "recipient_street")),
            @AttributeOverride(name = "number", column = @Column(name = "recipient_number")),
            @AttributeOverride(name = "complement", column = @Column(name = "recipient_complement")),
            @AttributeOverride(name = "name", column = @Column(name = "recipient_name")),
            @AttributeOverride(name = "phone", column = @Column(name = "recipient_phone"))
    })
    private ContactPoint recipient;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "delivery")
    private List<Item> items = new ArrayList<>();

    public static Delivery draft() {
        Delivery delivery = new Delivery();
        delivery.setId(UUID.randomUUID());
        delivery.setStatus(EnumDeliveryStatus.DRAFT);
        delivery.setTotalItems(0);
        delivery.setTotalCost(BigDecimal.ZERO);
        delivery.setCourierPayout(BigDecimal.ZERO);
        delivery.setDistanceFee(BigDecimal.ZERO);

        return delivery;
    }

    public UUID addItem(String name, int quantity) {
        Item item = Item.brandNew(name, quantity, this);
        items.add(item);
        calculateTotalItems();
        return item.getId();
    }

    public void removeItem(UUID itemId) {
        items.removeIf(item -> item.getId().equals(itemId));
        calculateTotalItems();
    }

    public void editPreparationDetails(PreparationDetails details) throws DomainException {
        verifyIfCanBeEdited();
        setSender(details.getSender());
        setRecipient(details.getRecipient());
        setDistanceFee(details.getDistanceFee());
        setCourierPayout(details.getCourierPayout());

        setExpectedDeliveryAt(OffsetDateTime.now().plus(details.getExpectedDeliveryTime()));
        setTotalCost(this.getDistanceFee().add(this.courierPayout));

    }

    public void place() throws DomainException {
        verifyIfCanBePlaced();
        this.changeStatusTo(EnumDeliveryStatus.WAITING_FOR_COURIER);
        this.setPlacedAt(OffsetDateTime.now());
        super.registerEvent(new DeliveryPlacedEvent(this.getPlacedAt(), this.getId()));
    }

    public void pickUp(UUID courierId) throws DomainException {
        this.setCourierId(courierId);
        this.changeStatusTo(EnumDeliveryStatus.IN_TRANSIT);
        this.setAssignedAt(OffsetDateTime.now());
        super.registerEvent(new DeliveryPickUpEvent(this.assignedAt, this.getId()));
    }

    public void markAsDelivery() throws DomainException {
        this.changeStatusTo(EnumDeliveryStatus.DELIVERED);
        this.setFulFilledAt(OffsetDateTime.now());
        super.registerEvent(new DeliveryFulFilledEvent(this.getFulFilledAt(), this.getId()));
    }

    public void removeItem() {
        items.clear();
        calculateTotalItems();
    }

    public List<Item> getItems() {
        return Collections.unmodifiableList(this.items);
    }

    private void calculateTotalItems() {
        Integer totalItems = getItems().stream().mapToInt(Item::getQuantity).sum();
        setTotalItems(totalItems);
    }

    private void verifyIfCanBePlaced() throws DomainException {
        if (!isFilled()) {
            throw new DomainException();
        }
        if (!getStatus().equals(EnumDeliveryStatus.DRAFT)) {
            throw new DomainException();
        }
    }

    private void verifyIfCanBeEdited() throws DomainException {
        if (!getStatus().equals(EnumDeliveryStatus.DRAFT)) {
            throw new DomainException();
        }
    }

    private boolean isFilled() {
        return this.getSender() != null && this.getRecipient() != null && this.totalCost != null;
    }

    private void changeStatusTo(EnumDeliveryStatus status) throws DomainException {
        if (status != null && this.getStatus().canNotChangeTo(status)) {
            throw new DomainException();
        }
        this.setStatus(status);
    }

    @AllArgsConstructor
    @Getter
    @Builder
    public static class PreparationDetails {
        private ContactPoint sender;
        private ContactPoint recipient;
        private BigDecimal distanceFee;
        private BigDecimal courierPayout;
        private Duration expectedDeliveryTime;
    }
}
