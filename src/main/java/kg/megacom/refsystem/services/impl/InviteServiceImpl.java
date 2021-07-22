package kg.megacom.refsystem.services.impl;

import kg.megacom.refsystem.dao.InviteRepository;
import kg.megacom.refsystem.enums.InviteStatus;
import kg.megacom.refsystem.mappers.InviteMapper;
import kg.megacom.refsystem.mappers.SubscriberMapper;
import kg.megacom.refsystem.models.Invite;
import kg.megacom.refsystem.models.dto.InviteDto;
import kg.megacom.refsystem.models.dto.SubscriberDto;
import kg.megacom.refsystem.services.InviteService;
import kg.megacom.refsystem.services.SubscriberService;
import kg.megacom.refsystem.services.exceptions.InviteLimitReached;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class InviteServiceImpl implements InviteService {
    @Autowired
    SubscriberService subscriberService;
    @Autowired
    InviteRepository inviteRepository;
    SubscriberMapper subscriberMapper = SubscriberMapper.INSTANCE;

    @Override
    public InviteDto sendInvite(InviteDto inviteDto) throws InviteLimitReached {


        inviteDto.setSender(subscriberService.getOrCreate(inviteDto.getSender()));
        inviteDto.setReceiver(subscriberService.getOrCreate(inviteDto.getReceiver()));

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, -1);


        long count = inviteRepository
                .countAllBySenderAndStartDateAfter(subscriberMapper.toSubscriber(inviteDto.getSender()),
                        calendar.getTime());

        if (count == 5) {
            throw new InviteLimitReached("Достигнуто максимально количество приглашении за сутки!");
        }
        calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        calendar.add(Calendar.HOUR_OF_DAY, 0);
        calendar.add(Calendar.MINUTE, 0);
        calendar.add(Calendar.SECOND, 0);
        calendar.add(Calendar.MILLISECOND, 0);

        count = inviteRepository
                .countAllBySenderAndStartDateAfter(subscriberMapper.toSubscriber(inviteDto.getSender()),
                        calendar.getTime());

        if (!inviteDto.getReceiver().getActive()) {
            throw new InviteLimitReached("Достигнуто максимально количество приглашении за месяц!");
        }

        count = inviteRepository
                .countAllBySenderAndStartDateAfter(subscriberMapper.toSubscriber(inviteDto.getReceiver()),
                        calendar.getTime());

        if(!inviteDto.getReceiver().getActive()){
            throw new InviteLimitReached("Абонент отключил возможность приглашения!");

        }
        if (!saveInvite(inviteDto)){
            throw new RuntimeException("Повторите попытку!");
        }
        if (!checkActiveInvite(inviteDto)){
            changeStatusDateAndInvite(inviteDto);
        }
        if (!checkStatusReceiver(inviteDto)){
            throw  new RuntimeException("Выбранный вами абонент заблокировал доступ к отправке приглошений!");
        }
        if (!CheckInviteDate(inviteDto)){
            throw new  RuntimeException("Уважаемый абонент вы отправляли invite выбранному абоненту! Повторите попытку через 24ч");
        }

        return null;
    }

    @Override
    public boolean acceptInviteAndChangeStatusOnAccept(SubscriberDto subscriberDto) {

        Invite invite = inviteRepository.findByReceiverIdAndInviteStatus(subscriberDto.getId(), InviteStatus.ACTIVE);
        if (invite==null){
            return false;
        }
        InviteDto inviteDto = InviteMapper.INSTANCE.toInviteDto(invite);
        inviteDto.setInviteStatus(InviteStatus.ACCEPTED);
        Calendar calendar = Calendar.getInstance();
        calendar = Calendar.getInstance();
        calendar.add(Calendar.YEAR,2999);
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        calendar.add(Calendar.HOUR_OF_DAY, 0);
        calendar.add(Calendar.MINUTE, 0);
        calendar.add(Calendar.SECOND, 0);
        calendar.add(Calendar.MILLISECOND, 0);
        Date date = new Date();
        date = calendar.getTime();
        inviteDto.setEndDate(date);

        inviteDto.setEndDate(date);
        Invite invite1 = inviteRepository.save(InviteMapper.INSTANCE.toInvite(inviteDto));
        System.out.println("Изменил статус " + invite1);
        return invite1.getInviteStatus().equals(InviteStatus.ACCEPTED);

    }

    private boolean saveInvite(InviteDto inviteDto){

        System.out.println("dfghjhgfdfghjk");
        inviteDto.setStartDate(new Date());
        inviteDto.setInviteStatus(InviteStatus.ACTIVE);
        Calendar calendar = Calendar.getInstance();
        calendar = Calendar.getInstance();
        calendar.add(Calendar.YEAR,2999);
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        calendar.add(Calendar.HOUR_OF_DAY, 0);
        calendar.add(Calendar.MINUTE, 0);
        calendar.add(Calendar.SECOND, 0);
        calendar.add(Calendar.MILLISECOND, 0);
        Date date = new Date();
        date = calendar.getTime();
        inviteDto.setEndDate(date);
        Invite invite = InviteMapper.INSTANCE.toInvite(inviteDto);
        Invite inviteToSave = inviteRepository.save(invite);
        InviteMapper.INSTANCE.toInviteDto(invite);
        return inviteToSave.getId() != null;
    }
    private boolean checkStatusReceiver(InviteDto inviteDto){

        List<SubscriberDto>subscriberDtos = subscriberService.selectAllSubscribers();
        System.out.println("Наш " +subscriberDtos);

        List<SubscriberDto>subscriberDtoList = subscriberDtos.stream()
                .filter(x -> x.getId().equals(inviteDto.getReceiver().getId()))
                .collect(Collectors.toList());
        System.out.println("Filter " + subscriberDtoList);
        for (SubscriberDto s : subscriberDtoList) {
            if (!s.getActive()){
                return false;
            }
        }
        return true;
    }

    private boolean CheckInviteDate(InviteDto inviteDto){
        Date date = new Date();
        Calendar calMid = Calendar.getInstance();
        calMid.set(Calendar.HOUR_OF_DAY,0);
        calMid.set(Calendar.MINUTE,0);
        calMid.set(Calendar.MILLISECOND,0);
        date =calMid.getTime();

        Calendar calNight = Calendar.getInstance();
        calNight.set(Calendar.YEAR,2999);
        calNight.set(Calendar.DAY_OF_MONTH,0);
        calNight.set(Calendar.HOUR_OF_DAY,0);
        calNight.set(Calendar.MINUTE,0);
        calNight.set(Calendar.SECOND,-1);
        calNight.set(Calendar.MILLISECOND,0);
        Date midNightToNight = calNight.getTime();
        List<Invite>invites = inviteRepository.findByStartDateBetweenAndSenderId(date,midNightToNight,inviteDto.getSender().getId());
        long count =invites.size();
        System.out.println(count);
        return count >=1;
    }

    private boolean checkActiveInvite(InviteDto inviteDto){
        List<Invite> forCheck = inviteRepository.findAll();
        List<Invite> invites = forCheck.stream()
                .filter(x->x.getReceiver().getPhone().equals(inviteDto.getReceiver().getPhone()))
                .collect(Collectors.toList());
        for (Invite i: invites){
            if (i.getReceiver().getPhone().equals(inviteDto.getReceiver().getPhone()) &&
                    i.getInviteStatus().equals(InviteStatus.ACTIVE)){
                return false;
            }
        }
    return true;
    }

    private void changeStatusDateAndInvite(InviteDto inviteDto){
    List<Invite> invites = inviteRepository.findAll();
    List<Invite>inviteList = invites.stream()
            .filter(x->x.getReceiver().getPhone().equals(inviteDto.getReceiver().getPhone()) &&
                    x.getInviteStatus().equals(InviteStatus.ACTIVE))
            .collect(Collectors.toList());
        Long id = null;
        for (Invite i : inviteList){
           id = i.getId();
        }
        assert id != null;

        Optional<Invite> optionalInvite = inviteRepository.findById(id);
        if (optionalInvite.isPresent()){
            Invite invite = optionalInvite.get();
            invite.setInviteStatus(InviteStatus.ACTIVE);
            Calendar calendar = Calendar.getInstance();
            Date date = new Date();
            calendar.setTime(date);
            calendar.add(Calendar.SECOND, -1);
            date = calendar.getTime();
            invite.setEndDate(date);
            inviteRepository.save(invite);
        }
    }

}

