package pl.demono10000.whencanwemeetbackend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.demono10000.whencanwemeetbackend.dto.InviteDto;
import pl.demono10000.whencanwemeetbackend.model.GroupInvitation;
import pl.demono10000.whencanwemeetbackend.service.InviteService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/invite")
public class InviteController {
    private final InviteService inviteService;

    @PostMapping
    public InviteDto inviteToGroup(@RequestBody InviteDto inviteDto) {
        return inviteService.send(inviteDto);
    }
}
