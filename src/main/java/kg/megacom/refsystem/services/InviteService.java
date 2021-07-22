package kg.megacom.refsystem.services;

import kg.megacom.refsystem.models.dto.InviteDto;
import kg.megacom.refsystem.models.dto.SubscriberDto;
import kg.megacom.refsystem.services.exceptions.InviteLimitReached;

public interface InviteService {

    InviteDto sendInvite(InviteDto inviteDto) throws InviteLimitReached;

    boolean acceptInviteAndChangeStatusOnAccept(SubscriberDto subscriberDto);
}
