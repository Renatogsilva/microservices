package com.br.renatorgs.redelivery.delivery.tracking.infraestructure.event;

public interface IntegrationEventPublisher {

    void publisher(Object event, String key, String topic);
}
