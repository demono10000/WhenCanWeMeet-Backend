package pl.demono10000.whencanwemeetbackend.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import pl.demono10000.whencanwemeetbackend.dto.InvitationResponseDto;
import pl.demono10000.whencanwemeetbackend.dto.InviteDto;
import pl.demono10000.whencanwemeetbackend.model.Group;
import pl.demono10000.whencanwemeetbackend.model.GroupInvitation;
import pl.demono10000.whencanwemeetbackend.model.User;
import pl.demono10000.whencanwemeetbackend.repository.GroupRepository;
import pl.demono10000.whencanwemeetbackend.repository.InviteRepository;
import pl.demono10000.whencanwemeetbackend.repository.UserRepository;
import pl.demono10000.whencanwemeetbackend.security.UserPrincipal;

@Service
@RequiredArgsConstructor
public class InviteService {
    private final InviteRepository inviteRepository;
    private final UserRepository userRepository;
    private final GroupRepository groupRepository;
    @Transactional
    public InviteDto send(InviteDto inviteDto) {
        UserService userService = new UserService(userRepository);
        User user = userService.findByUsername(inviteDto.username());
        GroupService groupService = new GroupService(groupRepository);
        Group group = groupService.getGroupById(inviteDto.groupId());
        GroupInvitation groupInvitation =  GroupInvitation.builder()
                .user(user)
                .group(group)
                .build();
        inviteRepository.save(groupInvitation);
        return inviteDto;
    }
    @Transactional
    public InvitationResponseDto respond(InvitationResponseDto invitationResponseDto) {
        if (invitationResponseDto.accepted()){
            GroupInvitation groupInvitation = inviteRepository.findById(invitationResponseDto.id()).orElseThrow();
            Group group = groupInvitation.getGroup();
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
            User user = userPrincipal.getUser();
            group.getUsers().add(user);
            user.getGroups().add(group);
            groupRepository.save(group);
            userRepository.save(user);
        }
        GroupInvitation groupInvitation = inviteRepository.findById(invitationResponseDto.id()).orElseThrow();
        inviteRepository.delete(groupInvitation);
        return invitationResponseDto;
    }
}
