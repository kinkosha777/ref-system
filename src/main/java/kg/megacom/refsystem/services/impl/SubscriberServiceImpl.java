package kg.megacom.refsystem.services.impl;

import kg.megacom.refsystem.dao.SubscriberRepository;
import kg.megacom.refsystem.mappers.SubscriberMapper;
import kg.megacom.refsystem.models.Subscriber;
import kg.megacom.refsystem.models.dto.SubscriberDto;
import kg.megacom.refsystem.services.InviteService;
import kg.megacom.refsystem.services.SubscriberService;
import kg.megacom.refsystem.services.exceptions.SubscriberNotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class SubscriberServiceImpl implements SubscriberService {
    @Autowired
    private SubscriberRepository subscriberRepository;
    private SubscriberMapper subscriberMapper = SubscriberMapper.INSTANCE;

    @Autowired
    private InviteService inviteService;



    @Override
    public SubscriberDto getOrCreate(SubscriberDto subscriberDto) {

            System.out.println(subscriberDto);
            Subscriber subscriber = subscriberMapper.toSubscriber(subscriberDto);


            try {


                subscriber = subscriberRepository.findById(subscriber.getId()).orElseThrow(SubscriberNotFound::new);
            }catch (SubscriberNotFound e){
                subscriber.setAddDate(new Date());
                subscriber.setEditDate(new Date());
                subscriber.setActive(true);
                subscriber = subscriberRepository.save(subscriber);

            }
            return subscriberMapper.toSubscriberDto(subscriber)  ;
        }

    @Override
    public SubscriberDto blockSubscriber(SubscriberDto subscriberDto) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss.SSS");
        Subscriber subscriber = SubscriberMapper.INSTANCE.toSubscriber(subscriberDto);
        if (subscriberRepository.existsById(subscriber.getId())){
            subscriber = subscriberRepository.findById(subscriber.getId()).get();
            subscriber.setActive(false);
            Date date = null;
            try {
                date = sdf.parse(sdf.format(new Date()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            subscriber.setEditDate(date);
            subscriberRepository.save(subscriber);
            return subscriberDto;
        }else {
            Date date = null;
            try {
                date = sdf.parse(sdf.format(new Date()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            subscriber.setActive(false);
            subscriber.setAddDate(date);
            subscriber.setEditDate(date);
            subscriber = subscriberRepository.save(subscriber);
            return subscriberDto;
        }

    }

    @Override
    public SubscriberDto acceptInvite(SubscriberDto subscriberDto) {

        SubscriberDto subscriberDto1 = getOrCreate(subscriberDto);
        if (inviteService.acceptInviteAndChangeStatusOnAccept(subscriberDto1)){
//                return new RuntimeException("Вы успешно приняли Invite!");
            return subscriberDto1;
            }
//            return new RuntimeException("У вас пока нет активных приглашений!");

    return subscriberDto1;
    }

    @Override
    public List<SubscriberDto> selectAllSubscribers() {
    List<Subscriber> subscribers = subscriberRepository.findAll();
        return  subscriberMapper.toSubscriberDtos(subscribers);
    }
}

