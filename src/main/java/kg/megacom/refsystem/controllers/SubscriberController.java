package kg.megacom.refsystem.controllers;

import kg.megacom.refsystem.models.dto.SubscriberDto;
import kg.megacom.refsystem.services.SubscriberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/subscriber")
public class SubscriberController {

    @Autowired
    private SubscriberService subscriberService;

    @PutMapping("/block")
    public SubscriberDto blocSubscriber(@RequestBody SubscriberDto subscriberDto){
        return subscriberService.blockSubscriber(subscriberDto);
    }

    @PutMapping("/accept")
    public SubscriberDto acceptInvite(@RequestBody SubscriberDto subscriberDto){
        return subscriberService.acceptInvite(subscriberDto);
    }
}