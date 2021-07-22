package kg.megacom.refsystem.services;

import kg.megacom.refsystem.models.dto.SubscriberDto;

import java.util.List;

public interface SubscriberService {

    SubscriberDto getOrCreate(SubscriberDto subscriberDto);

    SubscriberDto blockSubscriber(SubscriberDto subscriberDto);
    SubscriberDto acceptInvite(SubscriberDto subscriberDto);
    List<SubscriberDto> selectAllSubscribers();
}
