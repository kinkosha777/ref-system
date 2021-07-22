package kg.megacom.refsystem.models.dto;

import kg.megacom.refsystem.enums.InviteStatus;
import kg.megacom.refsystem.models.Subscriber;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;
@Data
public class InviteDto {

    private Long id;
    private SubscriberDto sender;
    private SubscriberDto receiver;
    private Date startDate;
    private Date endDate;
    private InviteStatus inviteStatus;
}
