package kg.megacom.refsystem.mappers;

import kg.megacom.refsystem.models.Subscriber;
import kg.megacom.refsystem.models.dto.SubscriberDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface SubscriberMapper {
    SubscriberMapper INSTANCE = Mappers.getMapper(SubscriberMapper.class);

    Subscriber toSubscriber(SubscriberDto subscriberDto);
    SubscriberDto toSubscriberDto(Subscriber subscriber);

    List<Subscriber> toSubscribers(List<SubscriberDto> subscriberDtos);
    List<SubscriberDto> toSubscriberDtos(List<Subscriber> subscribers);
}
