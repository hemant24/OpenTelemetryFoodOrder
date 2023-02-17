package com.talentica.dapr.orderservice.event;

import org.springframework.stereotype.Component;

@Component
public class DomainEventPublisherImpl implements DomainEventPublisher {

    @Override
    public void publish(String aggregateType, Object aggregateId, DomainEvent domainEvent) {

        System.out.println("will publish the domain event");

    }

}

