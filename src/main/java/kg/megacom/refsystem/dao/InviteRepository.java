package kg.megacom.refsystem.dao;

import kg.megacom.refsystem.enums.InviteStatus;
import kg.megacom.refsystem.models.Invite;
import kg.megacom.refsystem.models.Subscriber;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface InviteRepository extends JpaRepository<Invite, Long> {
    long countAllBySenderAndStartDateAfter(Subscriber sender, Date date);
    Invite findByReceiverIdAndInviteStatus(Long id, InviteStatus inviteStatus);
    List<Invite> findByStartDateBetweenAndSenderId(Date start, Date end, Long id);

}
