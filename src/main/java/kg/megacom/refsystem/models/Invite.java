package kg.megacom.refsystem.models;


import kg.megacom.refsystem.enums.InviteStatus;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "invites")
@Data
public class Invite {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "sender_sub_id")
    private Subscriber sender;
    @ManyToOne
    @JoinColumn(name = "receiver_sub_id")
    private Subscriber receiver;
    private Date startDate;
    private Date endDate;
    @Enumerated(EnumType.STRING)
    private InviteStatus inviteStatus;
}