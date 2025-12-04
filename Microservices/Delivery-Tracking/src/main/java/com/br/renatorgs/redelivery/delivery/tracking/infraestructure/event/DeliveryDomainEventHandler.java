package com.br.renatorgs.redelivery.delivery.tracking.infraestructure.event;

import com.br.renatorgs.redelivery.delivery.tracking.domain.event.DeliveryFulFilledEvent;
import com.br.renatorgs.redelivery.delivery.tracking.domain.event.DeliveryPickUpEvent;
import com.br.renatorgs.redelivery.delivery.tracking.domain.event.DeliveryPlacedEvent;
import com.br.renatorgs.redelivery.delivery.tracking.infraestructure.kafka.KafkaTopicConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;


@Component
@Slf4j
@RequiredArgsConstructor
public class DeliveryDomainEventHandler {

    private final IntegrationEventPublisher integrationEventPublisher;

    @EventListener
    public void handle(DeliveryPlacedEvent event) {
        log.info(event.toString());
        integrationEventPublisher.publisher(event, event.getDeliveryId().toString(), KafkaTopicConfig.deliveryEventsTopicName);
    }

    @EventListener
    public void handle(DeliveryPickUpEvent event) {
        log.info(event.toString());
        integrationEventPublisher.publisher(event, event.getDeliveryId().toString(), KafkaTopicConfig.deliveryEventsTopicName);
    }

    @EventListener
    public void handle(DeliveryFulFilledEvent event) {
        log.info(event.toString());
        integrationEventPublisher.publisher(event, event.getDeliveryId().toString(), KafkaTopicConfig.deliveryEventsTopicName);
    }
}
