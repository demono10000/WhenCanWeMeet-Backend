package pl.demono10000.whencanwemeetbackend.service;

import io.micrometer.common.util.StringUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import pl.demono10000.whencanwemeetbackend.dto.InvitationDto;
import pl.demono10000.whencanwemeetbackend.dto.InvitationResponseDto;
import pl.demono10000.whencanwemeetbackend.dto.InviteDto;
import pl.demono10000.whencanwemeetbackend.dto.StatusAndMessageDto;
import pl.demono10000.whencanwemeetbackend.model.Group;
import pl.demono10000.whencanwemeetbackend.model.GroupInvitation;
import pl.demono10000.whencanwemeetbackend.model.User;
import pl.demono10000.whencanwemeetbackend.repository.GroupRepository;
import pl.demono10000.whencanwemeetbackend.repository.InviteRepository;
import pl.demono10000.whencanwemeetbackend.repository.UserRepository;
import pl.demono10000.whencanwemeetbackend.security.UserPrincipal;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class InviteService {
    private final InviteRepository inviteRepository;
    private final UserRepository userRepository;
    private final GroupRepository groupRepository;
    @Transactional
    public StatusAndMessageDto send(InviteDto inviteDto) {
        // Validating the incoming data
        if (inviteDto == null) {
            return new StatusAndMessageDto(false, "Invite data cannot be null.");
        }

        if (StringUtils.isEmpty(inviteDto.getUsername())) {
            return new StatusAndMessageDto(false, "Username cannot be empty.");
        }

        if (inviteDto.getGroupId() == null) {
            return new StatusAndMessageDto(false, "Group ID cannot be null.");
        }

        // Fetching and validating the user
        User user;
        try{
            UserService userService = new UserService(userRepository);
            user = userRepository.findByUsername(inviteDto.getUsername()).get();
        }catch (Exception e){
            return new StatusAndMessageDto(false, "User not found.");
        }

        // Fetching and validating the group
        GroupService groupService = new GroupService(groupRepository, userRepository);
        Group group = groupService.getGroupById(inviteDto.getGroupId());

        if (group == null) {
            return new StatusAndMessageDto(false, "Group does not exist.");
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        User sender = userPrincipal.getUser();
        // Checking if the user is not in the group
        if (group.getUsers().contains(user)){
            return new StatusAndMessageDto(false, "User already in the group.");
        }

        // Checking if the inviter is the owner of the group
        if (!group.getOwner().getId().equals(sender.getId())) {
            return new StatusAndMessageDto(false, "Only the owner of the group can send invites.");
        }

        // Creating and saving the invitation
        GroupInvitation groupInvitation = GroupInvitation.builder()
                .user(user)
                .group(group)
                .build();

        try {
            inviteRepository.save(groupInvitation);
        } catch (DataIntegrityViolationException e) {
            return new StatusAndMessageDto(false, "Error during invite creation. Please try again.");
        }

        return new StatusAndMessageDto(true, "Invite sent successfully.");
    }

    @Transactional
    public StatusAndMessageDto respond(InvitationResponseDto invitationResponseDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        User user = userPrincipal.getUser();
        GroupInvitation groupInvitation = inviteRepository.findById(invitationResponseDto.id()).orElseThrow();
        if (!groupInvitation.getUser().getId().equals(user.getId())){
            return new StatusAndMessageDto(false, "You are not invited to this group.");
        }
        if (invitationResponseDto.accepted()){
            Group group = groupInvitation.getGroup();
            group.getUsers().add(user);
            user.getGroups().add(group);
            groupRepository.save(group);
            userRepository.save(user);
            inviteRepository.delete(groupInvitation);
            return new StatusAndMessageDto(true, "You have joined the group.");
        }
        inviteRepository.delete(groupInvitation);
        return new StatusAndMessageDto(true, "You have declined the invitation.");
    }
    @Transactional
    public InvitationDto[] list() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        User user = userPrincipal.getUser();
        GroupInvitation[] groupInvitations = inviteRepository.findAllByUser(user);
        InvitationDto[] invitationDtos = new InvitationDto[groupInvitations.length];
        for (int i = 0; i < groupInvitations.length; i++) {
            User owner = groupInvitations[i].getGroup().getOwner();
            invitationDtos[i] = new InvitationDto(groupInvitations[i].getGroup().getName(), owner.getUsername(), groupInvitations[i].getId());
        }
        return invitationDtos;
    }
}
