package kg.megacom.refsystem.controllers;


import kg.megacom.refsystem.models.dto.InviteDto;
import kg.megacom.refsystem.services.InviteService;
import kg.megacom.refsystem.services.exceptions.InviteLimitReached;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/invite")
public class InviteController {
    @Autowired
    private InviteService inviteService;


    @PostMapping("/send")
    public InviteDto send (@RequestBody InviteDto inviteDto) throws InviteLimitReached {
        System.out.println(inviteDto);
        return inviteService.sendInvite(inviteDto);
    }
}

